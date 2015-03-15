package ro.geenie.models;

import com.alamkanak.weekview.WeekViewEvent;

/**
 * Created by loopiezlol on 14.03.2015.
 */
public class WeekViewItemExtended {


    WeekViewEvent event;
    Boolean repeat;

    public WeekViewItemExtended(WeekViewEvent event, Boolean repeat) {
        this.event = event;
        this.repeat = repeat;
    }

    public WeekViewEvent getEvent() {
        return event;
    }

    public void setEvent(WeekViewEvent event) {
        this.event = event;
    }

    public Boolean getRepeat() {
        return repeat;
    }

    public void setRepeat(Boolean repeat) {
        this.repeat = repeat;
    }
}
