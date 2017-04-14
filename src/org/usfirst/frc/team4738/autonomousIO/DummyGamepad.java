package org.usfirst.frc.team4738.autonomousIO;

import java.util.Collections;

import org.usfirst.frc.team4738.enums.ControllerType;
import org.usfirst.frc.team4738.enums.Directions;
import org.usfirst.frc.team4738.interfaces.Gamepad;
import org.usfirst.frc.team4738.wrapper.Timer;
import org.usfirst.frc.team4738.wrapper.ToggleButton;

public class DummyGamepad implements Gamepad {

	protected ToggleButton[] buttons;

	protected FileManager fileManager;
	protected DataParser parse;
	protected String s;
	protected int port;
	protected int index;
	protected Timer timer;

	protected int controllerCount = 2;

	/**
	 * @param port Port for the recorded controller
	 * @param fileManager File manager with an autonomous file loaded
	 * @param controllerCount The number of controllerCount being used
	 */
	public DummyGamepad(int port, FileManager fileManager, int controllerCount) {
		this.fileManager = fileManager;
		parse = new DataParser();
		this.port = port;
		this.index = port;
		timer = new Timer();
		timer.start();
		this.controllerCount = controllerCount;
		
		parse.getControllerData(fileManager.lines.get(index)); // Updates the controller data for the first line.
		
		buttons = new ToggleButton[parse.buttons.size()];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new ToggleButton();
		}
	}
	
	/**
	 * Updates the parsed data for reading.
	 */
	public void updateData() {
		if (index < (fileManager.lines.size() - 1)) {
			s = fileManager.lines.get(index); // Gets the [index] line of the file
			
			if (timer.wait((double) parse.getTime(s))) { // If we are past the given time
				parse.getControllerData(s); // Parses controller data
				
				int i = 1;
				do {
					s = fileManager.lines.get(index + controllerCount*i); // Checks to find the most recent line.
					i++;
				} while (!timer.wait((double) parse.getTime(s)));
				index += controllerCount * i;
			}

		} else {
			Collections.fill(parse.axes, 0.0);
			Collections.fill(parse.buttons, false);
		}
	}

	/**
	 * Resets to the beginning of autonomous file.
	 */
	public void reset() {
		index = 0;
		timer.reset();
	}
	
	@Override
	public double getAxis(int axis) {
		return parse.axes.get(axis);
	}

	@Override
	public boolean getButton(int button) {
		return parse.buttons.get(button);
	}

	@Override
	public boolean getToggle(int button) {
		return buttons[button].getDownToggle(parse.buttons.get(button));
	}

	@Override
	public boolean getButtonUp(int button) {
		return buttons[button].getUp(parse.buttons.get(button));
	}

	@Override
	public boolean getButtonDown(int button) {
		return buttons[button].getDown(parse.buttons.get(button));
	}

	@Override
	public boolean getPOV(Directions direction) {
		return (parse.pov == direction.ordinal() * 45);
	}

	/**
	 * @return Returns the controller type. (ControllerType enum)
	 */
	@Override
	public ControllerType getControllerType() {
		if (buttons == null) {
			return ControllerType.GP;
		}

		for (ControllerType type : ControllerType.values()) {
			if (type.getButtonCount() == parse.buttons.size()) {
				return type;
			}
		}
		return ControllerType.GP;
	}

	@Override
	public int getButtonCount() {
		return parse.buttons.size();
	}
}
