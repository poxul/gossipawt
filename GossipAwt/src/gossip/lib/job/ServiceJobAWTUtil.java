package gossip.lib.job;

public class ServiceJobAWTUtil {

	@SuppressWarnings("deprecation")
	public static void invokeAWT(ServiceJobAWTDefault job) {
		job.run();
	}

}