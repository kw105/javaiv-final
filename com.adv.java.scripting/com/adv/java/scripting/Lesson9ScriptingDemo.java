/**
 * Demonstrate Java Scrpting skills learned through the UCSD Java IV course
 * @author Kevin
 */

package com.adv.java.scripting;

import javax.script.*;

public class Lesson9ScriptingDemo {

	public static void demonstrateScripting() {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("nashorn");

		try {
			System.out.println("Evaluate the function greet defined in a script");
			engine.eval("function greet(how,name) {return how + ', ' + name + '!'}");
			try {
				System.out.println("Invoke the function and print output to console");
				Object result = ((Invocable)engine).invokeFunction("greet", "Hello", "World");
				System.out.println((String)result);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		catch (ScriptException e) {
			e.printStackTrace();
		}


	}

}
