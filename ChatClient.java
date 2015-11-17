import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.net.*;

public class ChatClient extends JFrame{

	JButton sendButton;						//Creates a JButton ref
	JButton exitButton;						//Creates a JButton ref
	JTextPane output;						//Creates a JTextpane ref for output display
	JTextField input;						//Creates a JTextField for Input of text
	DatagramSocket dataIn=null;				//Creates a DatagramSocket ref
	String inputString="\\join";			//Declares string to store the user input
	InetAddress hostAddress=null;			//Declares an INetAddress ref to store the server IP
	static String hostName="localhost";	//Declares a string with the server name in it
	BufferedReader userInput=null;			//Declares a buffered reader to read from the input stream
	int server_port;						//Declares an int to store the port number of the sever
	ChatListener inputData=null;			//Declares a ref to a ChatListener object
	
	//*********************************************************************
	// ChatClient() is the constructor for the ChatClient Class and
	// performs all initialization operations for the ChatClient object
	// including initializing Variables, starting the ChatListener object,
	// setting up the GUI and connecting to the ChatServer program.
	//
	//********************************************************************
	public ChatClient(int pt){
		super("Chat Client");			//Calls the constructor of the super calls Jframe
		server_port = pt;
	        
		try{ 					
			//Create a new datagramSocket object to send and receive data
			dataIn = new DatagramSocket();
		}

		catch(SocketException e){
			//Do something sensible if I occur (i.e. recover if possible, otherwise exit with error message)
			//...
		}	
		//Catches any other types of errors that may occur
		catch(Exception e){
			//Do something sensible if I occur (i.e. recover if possible, otherwise exit with error message)
			//...
		} 
		try{
			//Uses the .getByName static method the InetAddress class
			//to obtain the address of the server 
			hostAddress = InetAddress.getByName(hostName);
		}
		//This catches an anKnownHostException which can occur if the host
		//cannot be found and exits if this occurs.
		catch(UnknownHostException e){
			//Do something sensible if I occur (i.e. recover if possible, otherwise exit with error message)
			//...
		}
	        byte[] sendData = null;
		try{
			//Create an initial datagramPacket that contains the \join Message which is sent
			//to the server so that the server knows to add the client to its database. This 
			//Packet is sent immediately so that the client will receive the data from the server
			//As soon as it is opened
		
                        sendData = inputString.getBytes("UTF-8");
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, hostAddress, server_port);
                        
			
			//Sends the intro Packet object via the dataIn DatagramSocket object using its send() method
			
			dataIn.send(sendPacket);
			
		}
		
		catch(IOException e){
			//Do something sensible if I occur (i.e. recover if possible, otherwise exit with error message)
		}
		
		
		
		//The following section sets up the GUI.... Alter the appearance as you wish
		
		setSize(510,600);		//Sets the size of the Chat programs main window(the JFrame object)	
		setResizable(false);	//Sets the resize flag to false so that the size of the window is fix
		//Creates a New JPannel which is the main window of the program
		JPanel chatWindow = new JPanel();
		//Sets the layout in the JPannel So that it is a BoxLayout along the Y axis
		chatWindow.setLayout(new BoxLayout(chatWindow, BoxLayout.Y_AXIS));	
		//Creates the JTextPane that display the Text from the server	
		output = new JTextPane();
		//Locks the output object for user input
		output.setEditable(false);
		//Sets the Size of the output Object
		output.setMaximumSize(new Dimension(500,100));
		output.setMinimumSize(new Dimension(500,100));
		output.setPreferredSize(new Dimension(500,100));
		//Sets the default text
		output.setText(""); //changed to nothing
		//Creates another JPanel with flow layout
		JPanel inputWindow = new JPanel();
		inputWindow.setLayout(new FlowLayout(FlowLayout.RIGHT));
		//Creates a scroll pane
		JScrollPane scroll = new JScrollPane(output);
		//Sets the title
		scroll.setBorder(new TitledBorder("Current Conversation"));
		//Sets the size of the scrollPane
		scroll.setMaximumSize(new Dimension(500,400));
		scroll.setMinimumSize(new Dimension(500,400));
		scroll.setPreferredSize(new Dimension(500,400));
		//Creates a Text field to input data
		input = new JTextField("Enter text here...");
		//Creates a scroll Pane
		JScrollPane scroll1 = new JScrollPane(input);
		//Sets the title of the Pane
		scroll1.setBorder(new TitledBorder("Message:"));
		//Sets the size of the Pane
		scroll1.setMaximumSize(new Dimension(495,100));
		scroll1.setMinimumSize(new Dimension(495,100));
		scroll1.setPreferredSize(new Dimension(495,100));
		
		//Creates a new Button to send that data to the server
		sendButton=new JButton("Send");
	
		//Here a window listener is added to detect if the user closes the program by pressing the
		//close button in the corner of the window.
		addWindowListener(new WindowAdapter(){
			//Creates an anonymous window adapter and implements the windowClosing method
			public void windowClosing(WindowEvent e){
				//Send disconnection message to the chat server
				try{
				DatagramPacket sendPacket2 = new DatagramPacket("\\disconnect".getBytes("UTF-8"),
				         "\\disconnect".getBytes("UTF-8").length, hostAddress, server_port);
                                dataIn.send(sendPacket2);
                                }catch(Exception err){System.out.println(err);}
				
				//Exit application
				System.exit(1);
				
				
			}
		});
	
		//Adds the action listener to the button that Picks up when the button is pressed
		sendButton.addActionListener(new ActionListener(){
			//Creates a new ActionListener object and implements to actionPerforemed method().
			public void actionPerformed(ActionEvent e){
				//Retrieves the text from the input field
				try{
				String text = input.getText();
				
				//Send to server in datagram
				
				DatagramPacket sendPacket2 = new DatagramPacket(text.getBytes("UTF-8"), text.getBytes("UTF-8").length, hostAddress, server_port);
                                dataIn.send(sendPacket2);
                                input.setText(""); //clear input
                                }catch(Exception err){System.out.println(err);}
			}
		});
	
		//Adds a key listener to the input object to detect the enter key being pressed	
		input.addKeyListener(new KeyListener(){
			//Creates a new KeyListener object and implements the keyPressed, KeyTyped and KeyReleased methods
			public void keyPressed(KeyEvent ke){
				//Checks to see if it was the enter Key pressed
				if(ke.getKeyCode()==KeyEvent.VK_ENTER){
					
				//Retrieves the text from the input field
				
				try{
				String text = input.getText();
				
				//Send to server in datagram
				
				DatagramPacket sendPacket2 = new DatagramPacket(text.getBytes("UTF-8"), text.getBytes("UTF-8").length, hostAddress, server_port);
                                dataIn.send(sendPacket2);
                                input.setText(""); //clear input
                                }catch(Exception err){System.out.println(err);}
					
				}
			}
			//Doesn't matter what these do as the keystrokes don't have to be captured
			//Function stubs are required to avoid compilation errors
			public void keyReleased(KeyEvent ke){; /*Leave me alone!*/}
			public void keyTyped(KeyEvent ke){; /*Leave me alone!*/}
		});	
		//Creates a new Button to exit the client
		exitButton=new JButton("Disconnect/Exit");
		//Adds the action listener to the button that Picks up when the button is pressed
		exitButton.addActionListener(new ActionListener(){
			//Creates a new ActionListener object and implements to actionPerforemed method()
			public void actionPerformed(ActionEvent e){
				
				//Send disconnection message to the chat server
				try{
				DatagramPacket sendPacket2 = new DatagramPacket("\\disconnect".getBytes("UTF-8"), "\\disconnect".getBytes("UTF-8").length, hostAddress, server_port);
                                dataIn.send(sendPacket2);
                                }catch(Exception err){System.out.println(err);}
				
				//Exit application
				System.exit(1);
			}
		});
	
		//Add everything to the GUI for display
		chatWindow.add(scroll);			//Adds scroll to the main panel
		inputWindow.add(scroll1);		//Adds scroll1 to the secondary panel
		inputWindow.add(exitButton);	//Adds the exit button to the secondary panel
		inputWindow.add(sendButton);	//Adds the send Button to the secondary panel
		chatWindow.add(inputWindow);	//Adds the secondary panel to the primary panel
		add(chatWindow);				//Adds the primary panel to the Jframe
		input.grabFocus();				//Puts the focus on the input object
		
		//Creates a new ChatListener object to listen for data from the server
		//NOTE: there is a call to the ChatListener object run() method in the constructor
		inputData = new ChatListener(dataIn, this);
		
		//Put focus on the send button
		sendButton.requestFocus();
		
	
	}
	//*************************************************************************
	// THis is the main() function of the ChatCLient program and it is the 
	// start point of the program. The whole program is kicked off by simply
	// Creating a new ChatClient object, which in turn calls chatClients
	// constructor which initiates all other parts of the ChatClient program
	//
	// Input: NONE
	// Output: NONE (to terminal)
	//
	//**************************************************************************
	public static void main( String args[]){
	        int port = 0;
		//Get server address and port number from command line args
		if(args.length != 2){
		        System.out.println("Usage: ChatClient <address> <port>");
		        System.exit(-1);
		}else {
		        hostName = args[0];
		        port = Integer.parseInt(args[1]);
		}
		
		ChatClient display = new ChatClient(port);	//Creates a ChatClient object to start the whole program
		
		display.setVisible(true);		//Sets the chatClient object to be visible
	}
	
	}
