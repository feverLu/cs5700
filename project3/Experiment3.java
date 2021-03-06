import java.util.Arrays;
import java.io.IOException;
import java.lang.InterruptedException;

public class Experiment3 extends Experiment {
	private String[] TCP_KIND = {"Reno", "SACK"};
	private String[] QUEUE_METHOD = {"DropTail", "RED"};
	private float tcp_start_time;
	private float tcp_end_time;
	private float cbr_start_time;
	private float cbr_end_time;
	private float indexTime;
	private float granuity = 0.5;

	private int i = 0, j = 0;
	private int tcpLen = TCP_KIND.length;
	private int queLen = QUEUE_METHOD.length;
	private float[][] brandwidths = new float[ESTIMATED_TCP_PACKET_NUMBER][3];
	private float[][] delays = new float[ESTIMATED_TCP_PACKET_NUMBER][3];

	// // To be used to caculate brandwidth
	// private int total_bytes;

	private int totalPacketReceived;

	private float[][] tcp_packet_sent_receive_time;
	private float total_latency;

	public Experiment3(float tcp_start_time, float tcp_end_time, float cbr_start_time, float cbr_end_time) {
		this.indexTime = tcp_start_time;
		this.tcp_start_time = tcp_start_time;
		this.tcp_end_time = tcp_end_time;
		this.cbr_start_time = cbr_start_time;
		this.cbr_end_time = cbr_end_time;
		initAllCaculatingValues();
	}

	public void initAllCaculatingValues() {
		totalPacketReceived = 0;

		tcp_packet_sent_receive_time = new float[ESTIMATED_TCP_PACKET_NUMBER][2];
		Arrays.fill(tcp_packet_sent_receive_time, INIT_SENT_TIME);

		total_latency = 0.0f;
	}

	public static void main(String[] args) {
		if(args.length() != 4) {
			System.out.print("Arguments: TCP start time, TCP end time, CBR start time, CBR end time");
			System.exit(1);
		}
		Experiment3 ex3 = new Experiment3(Float.parseFloat(args[0], Float.parseFloat(args[1]
										  Float.parseFloat(args[2], Float.parseFloat(args[3]);
		ex3.start();
	}

	public void start() {
		// Execute the script to generate the result files from NS
		for(i = 0; i < tcpLen; i++) {
			for(j = 0; j < queLen; j++) {
				String shellScript = "/course/cs4700f12/ns-allinone-2.35/bin/ns experiment3.tcl " + TCP_KIND[i] + " " + QUEUE_METHOD[j];
				try {
					Runtime.getRuntime().exec(shellScript);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}

		// Wait for all traces are ready
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

		// Parse the results
		for (i = 0; i < tcpLen; i++) {
			for (j = 0; j < queLen; j++) {
				readFile("my_experimental3_output_" + TCP_KIND[i] + "_" + QUEUE_METHOD[j] + ".tr");
				caculateThroughputAndLatency();
			}
		}

		StringBuilder brandwidthStr = new StringBuilder();
		StringBuilder delayStr = new StringBuilder();

		for(j = 0; j < tcp_packet_sent_receive_time.length; j++) {
			for(int i = 0; i < TCP_KIND.length; i++) {
				brandwidthStr.append(brandwidths[j][i]).append(" ");
				delayStr.append(delays[j][i]).append(" ");
			}
			brandwidthStr.append("\n");
            delayStr.append("\n");
		}
		Experiment.writeToFile("report/Report3_brandwidth", brandwidthStr.toString());
        Experiment.writeToFile("report/Report3_delay", delayStr.toString());
	}

	public void caculateThroughputAndLatency(float timeIndex, float total_latency, int totalPacketReceived) {
		int index = (int) (timeIndex - tcp_start_time) / granuity;
		int total_bytes = 1040 * totalPacketReceived;
		brandwidths[index][i] = (float) total_bytes / (9 * 1000 * 1000);
		delays[index][i] = total_latency / totalPacketReceived;
	}

	public void getFeed (String line) {
		String[] trace = line.split(" ");

		String messageType = trace[MESSAGE_TYPE];
		if (messageType.equals(DEQUEUED) && trace[SENDER].equals("0")) {
			float time = Float.parseFloat(trace[TIME]);

			// Only caculate packets sent after CBR flow starting
			if (time > tcp_start_time) {
				int seqNum = Integer.parseInt(trace[trace.length - 2]);

				// If the current float[] array size is not big enough, double it.
				if(seqNum >= tcp_packet_sent_receive_time.length) {
					increaseArraySize();
				}

				if(tcp_packet_sent_time[seqNum] == INIT_SENT_TIME) {
					tcp_packet_sent_receive_time[seqNum][0] = time;
				}
			}
 		} else if (messageType.equals(RECEIVED) && trace[RECEIVER].equals("0") && trace[PACKET_TYPE].equals(ACK)) {
 			float time = Float.parseFloat(trace[TIME]);
 			int seqNum = Integer.parseInt(trace[trace.length - 2]);

 			// If the sent time is -1.0, there is an error. Ignore it.
 			if(tcp_packet_sent_time[seqNum] != INIT_SENT_TIME) {
 				tcp_packet_sent_receive_time[seqNum][1] = time;
 				if (time < indexTime) {
 					total_latency += time - tcp_packet_sent_time[seqNum];
 					totalPacketReceived += 1;
 				} else {
 					caculateThroughputAndLatency(timeIndex, total_latency, totalPacketReceived);
 					indexTime += granuity;
 					total_latency = 0;
 					totalPacketReceived = 0
 				}
 				System.out.println("totalPacketReceived: " + totalPacketReceived);
 			}
		}
	}

	public void increaseArraySize () {
		float[][] temp = new float[tcp_packet_sent_time.length * 2][2];
		System.arraycopy(tcp_packet_sent_receive_time, 0, temp, 0, tcp_packet_sent_receive_time.length);
		tcp_packet_sent_receive_time = temp;
	}

	protected void result() {
		initAllCaculatingValues();
	}
}
