package com.android.todozen.editdate

import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.SharedRes
import com.android.todozen.core.domain.*
import com.android.todozen.databinding.DialogEditDateBinding
import com.android.todozen.databinding.LayoutDayBinding
import com.android.todozen.core.domain.DateTimeUtil.formatTime
import com.android.todozen.features.edittask.EditTaskViewModel
import com.android.todozen.expect.getString
import com.android.todozen.features.editdate.EditDateState
import com.android.todozen.features.editdate.EditDateViewModel
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import dev.icerock.moko.mvvm.utils.bindNotNull
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.YearMonth
import kotlinx.datetime.LocalDate as KotlinLocalDate
import java.time.LocalDate as JavaLocalDate

class EditDateDialog private constructor() : DialogFragment() {

    private val binding by viewBinding<DialogEditDateBinding>()
    private val editTaskViewModel by sharedViewModel<EditTaskViewModel>()
    private val viewModel by viewModel<EditDateViewModel>()
    private var state = EditDateState()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
        initListeners()
    }

    private fun initViews() = with(binding) {

        val localDate = arguments?.getLongArray(LOCAL_DATE)?.firstOrNull()
        viewModel.updateDate(localDate?.toLocalDate())
        val localTime = arguments?.getLongArray(LOCAL_TIME)?.firstOrNull()
        viewModel.updateTime(localTime?.toLocalTime())

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(100)
        val endMonth = currentMonth.plusMonths(100)
        val firstDayOfWeek = firstDayOfWeekFromLocale()
        calendar.setup(startMonth, endMonth, firstDayOfWeek)
        calendar.scrollToMonth(currentMonth)

        calendar.dayBinder = getDayBinder()

        btnTime.text = getString(SharedRes.strings.add_time)
        btnCancel.text = getString(SharedRes.strings.cancel)
        btnAccept.text = getString(SharedRes.strings.apply)
    }

    private fun initObservers() {
        viewModel.state.bindNotNull(this) {
            this.state = it
            binding.btnTime.text = formatTime(it.time).ifEmpty { getString(SharedRes.strings.add_time) }
        }
    }

    private fun initListeners() = with(binding) {
        btnAccept.setOnClickListener {
            editTaskViewModel.updateDateTime(state.date, state.time, state.repeat)
            dismiss()
        }
        btnCancel.setOnClickListener { dismiss() }
        btnTime.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                viewModel.updateTime(LocalTime.fromSecondOfDay(3600 * hour + 60 * minute))
            }
            val time = state.time ?: DateTimeUtil.now().time
            TimePickerDialog(context, timeSetListener, time.hour, time.minute, true).show()
        }
        btnTime.setOnLongClickListener { viewModel.updateTime(null); true }
        btnRepeat.setOnClickListener { viewModel.updateRepeat(RepeatType.DAILY) }
        btnRepeat.setOnLongClickListener { viewModel.updateRepeat(RepeatType.DEFAULT) ; true }
    }

    private fun getDayBinder() = object : MonthDayBinder<DayViewContainer> {

        override fun create(view: View) = DayViewContainer(view)

        override fun bind(container: DayViewContainer, data: CalendarDay) {

            container.day = data
            val textView = container.textView

            textView.text = data.date.dayOfMonth.toString()

            if (data.position == DayPosition.MonthDate) {
                // Show the month dates. Remember that views are reused!
                textView.visibility = View.VISIBLE
                if (data.date == state.date?.toJavaLocalDate()) {
                    // If this is the selected date, show a round background and change the text color.
                    textView.setTextColor(Color.WHITE)
                    textView.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.black
                        )
                    )
                } else {
                    // If this is NOT the selected date, remove the background and reset the text color.
                    textView.setTextColor(Color.BLACK)
                    textView.background = null
                }
            } else {
                // Hide in and out dates
                textView.visibility = View.INVISIBLE
            }

            if (data.position == DayPosition.MonthDate) {
                textView.setTextColor(Color.GREEN)
            } else {
                textView.setTextColor(Color.RED)
            }
            if (data.date == JavaLocalDate.now()) {
                textView.setTextColor(Color.BLUE)
            }
        }
    }

    inner class DayViewContainer(view: View) : ViewContainer(view) {

        val textView = LayoutDayBinding.bind(view).calendarDayText
        lateinit var day: CalendarDay

        init {
            view.setOnClickListener {
                if (day.position != DayPosition.MonthDate) return@setOnClickListener

                val currentSelection = state.date?.toJavaLocalDate()
                if (currentSelection == day.date) {
                    viewModel.updateDate(null)
                    binding.calendar.notifyDateChanged(currentSelection)
                } else {
                    viewModel.updateDate(day.date.toKotlinLocalDate())
                    binding.calendar.notifyDateChanged(day.date)
                    currentSelection?.let {
                        binding.calendar.notifyDateChanged(currentSelection)
                    }
                }
            }
        }
    }

    companion object {

        private const val LOCAL_DATE = "localDate"
        private const val LOCAL_TIME = "localTime"

        fun getInstance(
            localDate: KotlinLocalDate? = null,
            localTime: LocalTime? = null
        ) = EditDateDialog().apply {
            val bundle = bundleOf()
            localDate?.let { bundle.putLongArray(LOCAL_DATE, longArrayOf(localDate.toLong())) }
            localTime?.let { bundle.putLongArray(LOCAL_TIME, longArrayOf(localTime.toLong())) }
            arguments = bundle
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_edit_date, container, false)
}