#Create a simulator object
set ns [new Simulator]

# TCP kind
set variant [lindex $argv 0]
# Queue discipline
set queue_method [lindex $argv 1]

# Open the trace file (before starting the experiment)
set tf [open my_experimental3_output_${variant}_${queue_method}.tr w]
$ns trace-all $tf

# Close the trace file (after finishing the experiment)
proc finish() {} {
	global ns tf
	$ns flush-trace
	close $tf
	exit 0
}

# Create six nodes
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]
set n4 [$ns node]
set n5 [$ns node]
set n6 [$ns node]

# Create links between the nodes
$ns duplex-link $n1 $n2 10Mb 10ms $queue_method
$ns duplex-link $n2 $n5 10Mb 10ms $queue_method
$ns duplex-link $n2 $n3 10Mb 10ms $queue_method
$ns duplex-link $n3 $n4 10Mb 10ms $queue_method
$ns duplex-link $n3 $n6 10Mb 10ms $queue_method

# Setup a CBR over UDP at N5, set a null at N6 and connect them
set udp [new Agent/UDP]
$ns attach-agent $n5 $udp

set null [new Agent/Null]
$ns attach-agent $n6 $null
$ns connect $udp $null

set cbr [new Application/Traffic/CBR]
$cbr attach-agent $udp

$cbr set type_ CBR
$cbr set rate_ 8Mb

# Setup a TCP stream between N1 and n4
if {$variant eq "Reno"} {
	set tcp [new Agent/TCP/Reno]
	set sink [new Agent/TCPSink]
} elseif {$variant eq "SACK"} {
	set tcp [new Agent/TCP/Sack1]
	set sink [new Agent/TCPSink/Sack1]
}

# The class is the flow_id in output
$tcp set class_ 1
$ns attach-agent $n1 $tcp

$ns attach-agent $n4 $sink
$ns connect $tcp $sink

# Setup a FTP Application on TCP
set ftp [new Application/FTP]
$ftp attach-agent $tcp

# Schedule events for the CBR and TCP agents
$ns at 0.0 "$ftp start"
$ns at 1.0 "$cbr start"
$ns at 10.0 "$cbr stop"
$ns at 10.0 "$ftp stop"

$ns at 10.0 "finish"

# Run the simulation
$ns run







