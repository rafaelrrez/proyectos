package es.iescarrillo.project.idoctor2.datepickers;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

import java.time.LocalTime;
import java.util.Calendar;

import es.iescarrillo.project.idoctor2.R;

public class TimePickerHelper {

    public interface TimeSelectionListener {
        void onTimeSelected(LocalTime selectedTime, int callerId);
    }

    public static void showTimePickerDialog(Context context, int callerId, TimeSelectionListener listener) {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, R.style.DialogTheme,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        LocalTime selectedTime = LocalTime.of(hourOfDay, minute);
                        listener.onTimeSelected(selectedTime, callerId);
                    }
                },
                hourOfDay,
                minute,
                true
        );

        timePickerDialog.show();
    }
}
