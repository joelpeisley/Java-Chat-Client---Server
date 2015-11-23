package server;
import java.net.*;		//Needed for the DatagramSocket and DatagramClient classes
/*****************************************************************************
* This is a simple data storage class the stores the information that the
* server is required to know about an individual Client that is using
* the chat service. Each client is specified by its port and IP Address
* combination: both of these are stored in this object. The server can
* store an array of these objects: one for each client and when the server
* needs to send information out to its clients, it will simply traverse this
* array, sending the info to each client present in the array.
*
***************************************************************************/
public class ClientEntry{
	private int port;			//Stores the port number that the client is running on
	private InetAddress adr;	//Stores the IP address of the client Machine

	/**************************************************************
	* ClientEntry is a simple constructor
	* for the ClientEntry Class that sets the port and IP address 
	* to values specified at the time the object is constructed
	*
	* @param p The port
	* @param a The InetAddress (IP Address)
	*************************************************************/	
	ClientEntry(int p, InetAddress a){
		port = p;	//Sets the value of port
		adr = a;	//Sets the value of adr
	}
	/**
	 * This function is a simple getter that returns the value stored in port
	 * @return The port number
	 */
	public int getPort(){	
		return port;	//Returns the value stored in port
	 }
	/**
	 * This function is a simple getter that returns that value stored in adr
	 * @return The address of the client
	 */
	public InetAddress getAdr(){
		return adr;		//Returns adr
	}
}