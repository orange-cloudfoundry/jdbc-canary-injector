package data.loader;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import data.loader.domain.Data;
import data.loader.domain.Status;
import data.loader.repository.DataRepository;

@Component
public class ScheduledTasks {
	private static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final int TIMER = 5000;

	private Status status = Status.APP_START;

	@Autowired
	DataRepository dataRepository;

	/**
	 * @return
	 */
	private Data addNewData() {
		final Data data = new Data(this.status);
		this.dataRepository.save(data);
		return data;
	}

	/**
	 * @param data
	 */
	private void checkLastDataWasCorrectlyAdded(Data data) {

		final int timer = TIMER;
		final List<Data> datas = this.dataRepository.findFirst2ByOrderByDateDesc();
		if (datas == null) {
			this.logErrorNoRowsInTable();
			return;
		}
		if (datas.size() == 0) {
			this.logErrorNoRowsInTable();
			return;
		}
		if (datas.get(0).equals(data)) {
			LOG.error("ERROR: the last row of the table should be the row that was just added");
		}
		Date date = datas.remove(0).getDate();
		final Calendar before = Calendar.getInstance();
		final Calendar current = Calendar.getInstance();
		for (final Data d : datas) {
			before.setTime(date);
			before.set(Calendar.MILLISECOND, 0);
			current.setTime(d.getDate());
			current.set(Calendar.MILLISECOND, 0);
			before.add(Calendar.MILLISECOND, timer * -1);
			if (current.equals(before)) {
				LOG.info("Row " + d.getId() + " was added at " + d.getDate() + " (str=" + d.getStr() + ") and seams consistent with previous row");
			} else {
				if (this.status.equals(Status.APP_START)) {
					LOG.warn("Row " + d.getId() + " was added at " + d.getDate() + " (str=" + d.getStr() + ") and after starting this application");

				} else {
					LOG.error("Row " + d.getId() + " was added at " + d.getDate() + " (str=" + d.getStr()
					        + ") and does not seam consistent with previous row (delta=" + (before.getTimeInMillis() - current.getTimeInMillis()) + "ms)");
				}
			}
			date = d.getDate();
		}
	}

	/**
	 * @param data
	 * @return
	 */
	private boolean checkNewDataHasBeenCreatedInDatabase(Data data) {
		final Date date = data.getDate();
		final List<Data> datas = this.dataRepository.findByDate(date);
		if (datas == null) {
			this.logErrorDataDoesNotExist(date);
			return false;
		}
		if (datas.size() == 0) {
			this.logErrorDataDoesNotExist(date);
			return false;
		}

		boolean exist = false;
		while (!exist) {
			exist = this.findInList(datas, data);
		}
		if (!exist) {
			this.logErrorDataDoesNotExist(date);
			return false;
		}
		return true;
	}

	/**
	 * @param datas
	 * @param data
	 * @return
	 */
	private boolean findInList(final List<Data> datas, Data data) {
		for (final Data d : datas) {
			if (d.getStr() == data.getStr()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param date
	 */
	private void logErrorDataDoesNotExist(final Date date) {
		LOG.error("ERROR: the row with date " + date + " does not exist in database.");
	}

	/**
	 *
	 */
	private void logErrorNoRowsInTable() {
		LOG.error("ERROR: there are no rows in the table");
	}

	/**
	 *
	 */
	@Scheduled(fixedRate = TIMER)
	public void task() {
		final Data data = this.addNewData();
		this.checkNewDataHasBeenCreatedInDatabase(data);
		this.checkLastDataWasCorrectlyAdded(data);
		this.status = Status.TIMER;
	}
}
