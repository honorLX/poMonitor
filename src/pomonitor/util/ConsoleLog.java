package pomonitor.util;

public class ConsoleLog {
	public static void PrintInfo(Class clazz, String info) {
		System.out.println("###### Info from " + clazz.getName() + ": " + info);
	}

	public static void PrintErr(Class clazz, String info) {
		System.out
				.println("###### Error from " + clazz.getName() + ": " + info);
	}
}
