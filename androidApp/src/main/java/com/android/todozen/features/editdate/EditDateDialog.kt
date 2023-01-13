package com.android.todozen.features.editdate

import android.app.TimePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.utils.toLocalDate
import com.android.todozen.utils.toLocalTime
import com.android.todozen.utils.toLong
import com.android.todozen.databinding.DialogEditDateBinding
import com.android.todozen.databinding.LayoutDayBinding
import com.android.todozen.utils.DateTimeUtil
import com.android.todozen.utils.DateTimeUtil.formatTimeOrEmpty
import com.android.todozen.features.edittask.EditTaskViewModel
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import dev.icerock.moko.mvvm.utils.bind
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.time.YearMonth
import kotlinx.datetime.LocalDate as KotlinLocalDate
import java.time.LocalDate as JavaLocalDate

class EditDateDialog private constructor() : DialogFragment() {

    private val binding by viewBinding<DialogEditDateBinding>()
    private val editTaskViewModel by sharedViewModel<EditTaskViewModel>()
    private val editDateViewModel by sharedViewModel<EditDateViewModel>()

    private var state = EditDateState()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editDateViewModel.state.bind(this) { state -> state?.let { render(state) } }

        arguments?.getLongArray(LOCAL_DATE)?.let {
            editDateViewModel.updateDate(it.firstOrNull()?.toLocalDate())
        }

        arguments?.getLongArray(LOCAL_TIME)?.let {
            editDateViewModel.updateTime(it.firstOrNull()?.toLocalTime())
        }


        initViews()
        initListeners()
    }

    private fun render(state: EditDateState) {
        this.state = state
        binding.btnTime.text = state.time?.let { formatTimeOrEmpty(it) } ?: "+ Добавить время"
    }

    inner class DayViewContainer(view: View) : ViewContainer(view) {

        val textView = LayoutDayBinding.bind(view).calendarDayText
        lateinit var day: CalendarDay

        init {
            view.setOnClickListener {
                if (day.position == DayPosition.MonthDate) {
                    // Keep a reference to any previous selection
                    // in case we overwrite it and need to reload it.
                    val currentSelection = state.date?.toJavaLocalDate()
                    if (currentSelection == day.date) {
                        // If the user clicks the same date, clear selection.
                        editDateViewModel.updateDate(null)
                        // Reload this date so the dayBinder is called
                        // and we can REMOVE the selection background.
                        binding.calendar.notifyDateChanged(currentSelection)
                    } else {
                        editDateViewModel.updateDate(day.date.toKotlinLocalDate())
                        // Reload the newly selected date so the dayBinder is
                        // called and we can ADD the selection background.
                        binding.calendar.notifyDateChanged(day.date)
                        currentSelection?.let {
                            binding.calendar.notifyDateChanged(currentSelection)
                        }
                    }
                }
            }
        }
    }

    private fun initViews() = with(binding) {

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(100)  // Adjust as needed
        val endMonth = currentMonth.plusMonths(100)  // Adjust as needed
        val firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
        calendar.setup(startMonth, endMonth, firstDayOfWeek)
        calendar.scrollToMonth(currentMonth)

        calendar.dayBinder = object : MonthDayBinder<DayViewContainer> {

            override fun create(view: View) = DayViewContainer(view)

            override fun bind(container: DayViewContainer, data: CalendarDay) {

                container.day = data
                val day = data
                val textView = container.textView

                textView.text = day.date.dayOfMonth.toString()

                if (day.position == DayPosition.MonthDate) {
                    // Show the month dates. Remember that views are reused!
                    textView.visibility = View.VISIBLE
                    if (day.date == state.date?.toJavaLocalDate()) {
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
                    container.textView.setTextColor(Color.GREEN)
                } else {
                    container.textView.setTextColor(Color.RED)
                }
                if (data.date == JavaLocalDate.now()) {
                    container.textView.setTextColor(Color.BLUE)
                }
            }
        }
    }

    private fun initListeners() = with(binding) {
        btnAccept.setOnClickListener {
            Log.d("aboba", "$state")
            editTaskViewModel.updateDateTime(state.date, state.time)
            dismiss()
        }
        btnCancel.setOnClickListener { dismiss() }
        btnTime.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                editDateViewModel.updateTime(LocalTime.fromSecondOfDay(3600 * hour + 60 * minute))
            }
            val time = state.time ?: DateTimeUtil.now().time
            TimePickerDialog(context, timeSetListener, time.hour, time.minute, true).show()
        }
        btnTime.setOnLongClickListener {
            editDateViewModel.updateTime(null)
            true
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        editDateViewModel.clearState()
    }
}