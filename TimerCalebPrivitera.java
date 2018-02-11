
package a1;

public class TimerCalebPrivitera {

	long start, stop;

	public void start() {
		start = System.currentTimeMillis();
	}

	public void stop() {
		stop = System.currentTimeMillis();
	}

	public long getDuration() {
		return (stop - start);
	}

}
