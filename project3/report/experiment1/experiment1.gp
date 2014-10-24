# global settings for experiment1
set terminal png
set termoption enhanced
set grid

# settings for plotting experiment1 throughput
set output "exp1-thp.png"
set title "Experiment 1 - Throughput"
set xlabel "CBR (Mbps)"
set ylabel "Throughput (MB)"
set xrange[1:]
set yrange[0:]
plot "Report1_throughput" using 1 with lines title "Tahoe", \
     "Report1_throughput" using 2 with lines title "Reno", \
     "Report1_throughput" using 3 with lines title "NewReno", \
     "Report1_throughput" using 4 with lines title "Vegas"
unset output

# settings for plotting experiment1 drop rate
set output "exp1-dr.png"
set title "Experiment 1 - Packets Drop Rate"
set xlabel "CBR (Mbps)"
set ylabel "Packets Drop Rate (Percentage)"
set xrange[1:]
set yrange[0:]
plot "Report1_droprate" using 1 with lines title "Tahoe", \
     "Report1_droprate" using 2 with lines title "Reno", \
     "Report1_droprate" using 3 with lines title "NewReno", \
     "Report1_droprate" using 4 with lines title "Vegas"
unset output

# settings for plotting experiment1 latency
set output "exp1-lt.png"
set title "Experiment 1 - Latency"
set xlabel "CBR (Mbps)"
set ylabel "Average Latency (ms)"
set xrange[1:]
set yrange[0:]
plot "Report1_delay" using 1 with lines title "Tahoe", \
     "Report1_delay" using 2 with lines title "Reno", \
     "Report1_delay" using 3 with lines title "NewReno", \
     "Report1_delay" using 4 with lines title "Vegas"
unset output
