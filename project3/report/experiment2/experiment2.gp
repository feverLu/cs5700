# global settings for experiment1
set terminal png
set termoption enhanced
set grid

# settings for plotting experiment2 throughput
set output "exp2-thp-Reno-Reno.png"
set title "Experiment 2 - Throughput: Reno/Reno"
set xlabel "CBR (Mbps)"
set ylabel "Throughput (MB)"
plot "Report2_throughput_Reno_Reno" using 1 with lines title "Reno-1", \
     "Report2_throughput_Reno_Reno" using 2 with lines title "Reno-2"
unset output

# settings for plotting experiment2 throughput
set output "exp2-thp-NewReno-Reno.png"
set title "Experiment 2 - Throughput: NewReno/Reno"
set xlabel "CBR (Mbps)"
set ylabel "Throughput (MB)"
plot "Report2_throughput_NewReno_Reno" using 1 with lines title "NewReno", \
     "Report2_throughput_NewReno_Reno" using 2 with lines title "Reno"
unset output

# settings for plotting experiment2 throughput
set output "exp2-thp-Vegas-Vegas.png"
set title "Experiment 2 - Throughput: Vegas/Vegas"
set xlabel "CBR (Mbps)"
set ylabel "Throughput (MB)"
plot "Report2_throughput_Vegas_Vegas" using 1 with lines title "Vegas-1", \
     "Report2_throughput_Vegas_Vegas" using 2 with lines title "Vegas-2"
unset output

# settings for plotting experiment2 throughput
set output "exp2-thp-NewReno-Vegas.png"
set title "Experiment 2 - Throughput: NewReno/Vegas"
set xlabel "CBR (Mbps)"
set ylabel "Throughput (MB)"
plot "Report2_throughput_NewReno_Vegas" using 1 with lines title "NewReno", \
     "Report2_throughput_NewReno_Vegas" using 2 with lines title "Vegas"
unset output



# settings for plotting experiment2 drop rate
set output "exp2-dr-Reno-Reno.png"
set title "Experiment 2 - Packets Drop Rate: Reno/Reno"
set xlabel "CBR (Mbps)"
set ylabel "Packets Drop Rate (Percentage)"
plot "Report2_droprate_Reno_Reno" using 1 with lines title "Reno-1", \
     "Report2_droprate_Reno_Reno" using 2 with lines title "Reno-2"
unset output

# settings for plotting experiment2 drop rate
set output "exp2-dr-NewReno-Reno.png"
set title "Experiment 2 - Packets Drop Rate: NewReno/Reno"
set xlabel "CBR (Mbps)"
set ylabel "Packets Drop Rate (Percentage)"
plot "Report2_droprate_NewReno_Reno" using 1 with lines title "NewReno", \
     "Report2_droprate_NewReno_Reno" using 2 with lines title "Reno"
unset output

# settings for plotting experiment2 drop rate
set output "exp2-dr-Vegas-Vegas.png"
set title "Experiment 2 - Packets Drop Rate: Vegas/Vegas"
set xlabel "CBR (Mbps)"
set ylabel "Packets Drop Rate (Percentage)"
plot "Report2_droprate_Vegas_Vegas" using 1 with lines title "Vegas-1", \
     "Report2_droprate_Vegas_Vegas" using 2 with lines title "Vegas-2"
unset output

# settings for plotting experiment2 drop rate
set output "exp2-dr-NewReno-Vegas.png"
set title "Experiment 2 - Packets Drop Rate: NewReno/Vegas"
set xlabel "CBR (Mbps)"
set ylabel "Packets Drop Rate (Percentage)"
plot "Report2_droprate_NewReno_Vegas" using 1 with lines title "NewReno", \
     "Report2_droprate_NewReno_Vegas" using 1 with lines title "Vegas"
unset output


# settings for plotting experiment2 latency
set output "exp2-lt-Reno-Reno.png"
set title "Experiment 2 - Latency: Reno/Reno"
set xlabel "CBR (Mbps)"
set ylabel "Average Latency (ms)"
plot "Report2_delay_Reno_Reno" using 1 with lines title "Reno-1", \
     "Report2_delay_Reno_Reno" using 2 with lines title "Reno-2"
unset output

# settings for plotting experiment2 latency
set output "exp2-lt-NewReno-Reno.png"
set title "Experiment 2 - Latency: NewReno/Reno"
set xlabel "CBR (Mbps)"
set ylabel "Average Latency (ms)"
plot "Report2_delay_NewReno_Reno" using 1 with lines title "NewReno", \
     "Report2_delay_NewReno_Reno" using 2 with lines title "Reno"
unset output

# settings for plotting experiment2 latency
set output "exp2-lt-Vegas-Vegas.png"
set title "Experiment 2 - Latency: Vegas/Vegas"
set xlabel "CBR (Mbps)"
set ylabel "Average Latency (ms)"
plot "Report2_delay_Vegas_Vegas" using 1 with lines title "Vegas-1", \
     "Report2_delay_Vegas_Vegas" using 2 with lines title "Vegas-2"
unset output

# settings for plotting experiment2 latency
set output "exp2-lt-NewReno-Vegas.png"
set title "Experiment 2 - Latency: NewReno/Vegas"
set xlabel "CBR (Mbps)"
set ylabel "Average Latency (ms)"
plot "Report2_delay_NewReno_Vegas" using 1 with lines title "NewReno", \
     "Report2_delay_NewReno_Vegas" using 2 with lines title "Vegas"
unset output
