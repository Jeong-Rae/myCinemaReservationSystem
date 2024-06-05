package feature.screeningschedule;

import core.domain.screeningschedule.ScreeningSchedule;

public interface ScheduleListViewModelDelegate {
	void scheduleListCellTapped(ScreeningSchedule schedule);
}
