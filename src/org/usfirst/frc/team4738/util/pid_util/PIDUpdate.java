package org.usfirst.frc.team4738.util.pid_util;

import java.util.TimerTask;

public class PIDUpdate extends TimerTask {

	private PID pid;
	private ErrorRetriever err;
	
	PIDUpdate(PID pid, ErrorRetriever err) {
		this.pid = pid;
		this.err = err;
	}

	@Override
	public void run() {
		pid.update(err.getError());
	}

}
