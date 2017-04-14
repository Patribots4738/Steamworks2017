package org.usfirst.frc.team4738.autonomousIO;

import java.util.ArrayList;
import java.util.Scanner;

import org.usfirst.frc.team4738.enums.ControllerType;

/**
 * Class dedicated to parsing data from data files to be used to control the robot.
 * @author Ghjf544912
 */
public class DataParser {

	private Scanner sc;
	public ArrayList<Double> axes;
	public ArrayList<Boolean> buttons;
	public ArrayList<Double> encoders;
	public int pov = -1;
	public int port = -1;
	public int time = 0;
	
	public ControllerType getControllerType(){
		if(buttons == null){
			return ControllerType.GP;
		}
		
		for(ControllerType type : ControllerType.values()){
			if(type.getButtonCount() == buttons.size()){
				return type;
			}
		}
		return ControllerType.GP;
	}
	
	/**
	 * @param s String from Filer.readNextLine() to parse.
	 * @return An array list of doubles to be used as axis values.
	 */
	public ArrayList<Double> getNextAxes(String s){
		axes = new ArrayList<Double>();
		axes.removeAll(axes);
		sc = new Scanner(s);
		sc.useDelimiter(",");
		while(sc.hasNextDouble()){
			axes.add(sc.nextDouble());
		}
		sc.close();
		return axes;
	}
	
	public void getControllerData(String s){
		try{
			axes = new ArrayList<>(); // Initializes the data arrays and scanner
			buttons = new ArrayList<>();
			sc = new Scanner(s);
			sc.useDelimiter(",");
			port = sc.nextInt(); // Reads the first integer as port
			while (sc.hasNextDouble()) { // Reads as many doubles as it can (for axes)
				double val = sc.nextDouble();
				
				axes.add(val);
//				System.out.println("Val: " + val + " Index: " + (axes.size()-1));
			}
//			System.out.println(axes.size());
			while(sc.hasNextBoolean()){ // Reads as many booleans as it can (for buttons)
				buttons.add(sc.nextBoolean());
			}
			pov = sc.nextInt(); // Reads the directional pad integer
			time = sc.nextInt(); // Reads the timestamp for timings.
			sc.close();
		}catch(Exception e){
			sc.close();
			e.printStackTrace();
		}
	}
	
	/**
	 * @param s String from Filer.readNextLine() to parse.
	 * @return An array list of booleans to be used as button values.
	 * @deprecated Use getControllerData instead
	 */
	public ArrayList<Boolean> getNextButtons(String s){
		buttons = new ArrayList<Boolean>();
		if(!buttons.isEmpty())
			buttons.removeAll(buttons);
		sc = new Scanner(s);
		sc.useDelimiter(",");
		while(sc.hasNextBoolean()){
			buttons.add(sc.nextBoolean());
		}
		sc.close();
		return buttons;
	}
	
	/**
	 * @param s String from Filer.readNextLine() to parse.
	 * @return An array list of doubles to be used as encoder speeds.
	 * @deprecated Use getControllerData instead
	 */
	public ArrayList<Double> getNextEncoders(String s){
		encoders = new ArrayList<Double>();
		if(!encoders.isEmpty())
			encoders.removeAll(encoders);
		sc = new Scanner(s);
		sc.useDelimiter(",");
		while(sc.hasNextDouble()){
			encoders.add(sc.nextDouble());
		}
		sc.close();
		return encoders;
	}
	
	/**
	 * @param s String from Filer.readNextLine() to parse.
	 * @return An an int to be used as the port switch.
	 */
	public int getPort(String s){
		
		sc = new Scanner(s);
		sc.useDelimiter(",");
		if(sc.hasNextInt()){
			port = sc.nextInt();
		} else{
			return -1;
		}
		sc.close();
		return port;
	}
	
	/**
	 * @param s String from Filer.readNextLine() to parse.
	 * @return An int to be used as POV switch.
	 * @deprecated Use getControllerData instead
	 */
	public int getNextPOV(String s){
		sc = new Scanner(s);
		sc.useDelimiter(",");
		if(sc.hasNextInt()){
			sc.nextInt();
			if(sc.hasNextInt()){
				pov = sc.nextInt();
			} else{
				return -1;
			}
		}else{
			return -1;
		}
		sc.close();
		return pov;
	}
	
	/**
	 * @param s Line of data from the generated autonomous file
	 * @return the timestamp of the control.
	 */
	public int getTime(String s){
		sc = new Scanner(s);
		sc.useDelimiter(",");
		if(sc.hasNextInt()){
			sc.nextInt();
			if(sc.hasNextInt()){//x        <-- this comment means nothing
				sc.nextInt();
				if(sc.hasNextInt()){
					time =  sc.nextInt();
				}else{
					return -1;
				}
			} else{
				return -1;
			}
		}else{
			return -1;
		}
		sc.close();
		return time;
	}
}