import java.io.*;		//Needed for general input and output classes
import java.net.*;		//Needed for the DatagramSocket and DatagramClient classes
//*****************************************************************************
// Class ClientEntry
// This is a simple data storage class the stores the information that the
// server is required to know about an individual Client that is using
// The chat service. Each client is specified by its port and IP Address
// Combination: both of these are stored in this object. The Server can
// store an array of these objects: one for each client and when the server
// needs to send information out to its clients, it simply traverse this
// array, sending the info to each client present in the array.
//
//
//***************************************************************************
public class ClientEntry{
	private int port;			//Stores the port number that the client is running on
	private InetAddress adr;	//Stores the IP address of the client Machine

	//**************************************************************
	// ClientEntry(int p, InetAddress a) is a simple constructor
	// For the ClientEntry Class that sets the port and IP address 
	// to values specified at the time the object is constructed
	//
	//*************************************************************	
	ClientEntry(int p, InetAddress a){
		port = p;	//Sets the value of port
		adr = a;	//Sets the value of adr
	}
	//This function is a simple getter that returns the value stored in port
	public int getPort(){	
		return port;	//Returns the value stored in port
	 }
	//This function is a simple getter that returns that value stored in adr
	public InetAddress getAdr(){
		return adr;		//Returns adr
	}
}