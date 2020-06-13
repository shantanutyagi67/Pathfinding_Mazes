package mazes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JFrame;

class Player{
	int x=0, y= 0;
}

public class maze extends JComponent implements Runnable, KeyListener, MouseListener{

	private static final long serialVersionUID = 1L;
//this uses DFS to generate a randomised maze
	maze(){
		addKeyListener(this);
		setFocusable(true);
	}
	static JFrame frame = new JFrame("Maze 1");
	static int size = 25;
	Player p1 = new Player();
	static Vector<Vector<Vector<Integer>>> maze = new Vector<Vector<Vector<Integer>>>();
	static Vector<Vector<Vector<Integer>>> padosiI = new Vector<Vector<Vector<Integer>>>();
	static Vector<Vector<Vector<Integer>>> padosiJ = new Vector<Vector<Vector<Integer>>>();
	static Vector<Vector<Boolean>> visited = new Vector<Vector<Boolean>>();
	static Vector<Integer> stackI = new Vector<Integer>();
	static Vector<Integer> stackJ = new Vector<Integer>();
	static int currentCellI=0, currentCellJ=0;//select initial cell
	boolean start = false, end = false;
	public static void main(String args[]) {
//Random maze wall digits
//		for(int i=0;i<size;i++) {
//			for(int j=0;j<size;j++) {
//				maze.get(i).get(j).set(0,(new Random()).nextInt(Integer.MAX_VALUE)%2);
//				maze.get(i).get(j).set(1,(new Random()).nextInt(Integer.MAX_VALUE)%2);
//				maze.get(i).get(j).set(2,(new Random()).nextInt(Integer.MAX_VALUE)%2);
//				maze.get(i).get(j).set(3,(new Random()).nextInt(Integer.MAX_VALUE)%2);
//			}
//		}
		//Initialize
				//Initialize();
		//maze Generation using recursive back tracker
				//makeMaze();
		//display debug info
//		for(int k=0;k<size;k++) {
//			for(int l=0;l<size;l++) {
//				System.out.print(maze.get(k).get(l)+" ");
//			}
//			System.out.println();
//		}
//		for(int k=0;k<size;k++) {
//			for(int l=0;l<size;l++) {
//				System.out.print(visited.get(k).get(l)+" ");
//			}
//			System.out.println();
//		}
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0,0,800,800);
		frame.getContentPane().add(new maze());
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setVisible(true);
	}
	private static void Initialize() {
		for(int i=0;i<size;i++) {//visited array
			visited.add(new Vector<Boolean>());
			for(int j=0;j<size;j++) {
				visited.get(i).add(false);
			}
		}
		for(int i=0;i<size;i++) {//adding 4 walls to each cell
			maze.add(new Vector<Vector<Integer>>(size));
			for(int j=0;j<size;j++) {
				maze.get(i).add(new Vector<Integer>());
				maze.get(i).get(j).add(1);//N wall
				maze.get(i).get(j).add(1);//S wall
				maze.get(i).get(j).add(1);//E wall
				maze.get(i).get(j).add(1);//W wall
			}
		}
		for(int i=0;i<size;i++) {//Neighbors of each cell (2-4 values depending on corner, edge, middle cell)
			padosiI.add(new Vector<Vector<Integer>>(size));
			padosiJ.add(new Vector<Vector<Integer>>(size));
			for(int j=0;j<size;j++) {
				padosiI.get(i).add(new Vector<Integer>());
				padosiJ.get(i).add(new Vector<Integer>());
				if(j+1<size) {
					padosiI.get(i).get(j).add(i);
					padosiJ.get(i).get(j).add(j+1);
				}
				if(i+1<size) {
					padosiI.get(i).get(j).add(i+1);
					padosiJ.get(i).get(j).add(j);
				}
				if(j-1>=0) {
					padosiI.get(i).get(j).add(i);
					padosiJ.get(i).get(j).add(j-1);
				}
				if(i-1>=0) {
					padosiI.get(i).get(j).add(i-1);
					padosiJ.get(i).get(j).add(j);
				}
			}
		}
	}
	private static void makeMaze() {
		visited.get(currentCellI).set(currentCellJ,true);//set initial cell as visited
		stackI.add(currentCellI);//add initial cell to stack
		stackJ.add(currentCellJ);
		while(!stackI.isEmpty()) {//loop till stack is empty
			//pop stack and set the popped value to current cell
			currentCellI=stackI.get(stackI.size()-1);//setting  current cell 
			currentCellJ=stackJ.get(stackJ.size()-1);
			stackI.remove(stackI.size()-1);//popping of stack
			stackJ.remove(stackJ.size()-1);
			int rand;
			int cnt =padosiI.get(currentCellI).get(currentCellJ).size();//total neighbours of current cell
			int cnt2=0;//number of unvisited neighbours current cell has
			while(cnt!=0) {
				cnt--;
				if(visited.get(padosiI.get(currentCellI).get(currentCellJ).get(cnt)).get(padosiJ.get(currentCellI).get(currentCellJ).get(cnt))==false) {
					cnt2++;
				}
			}
			//if current cell has unvisited neighbours
			if(cnt2>0) {
				do {//find random unvisited neighbour of current cell and storing its index in rand variable
					rand = (new Random()).nextInt(padosiI.get(currentCellI).get(currentCellJ).size());
				}while(visited.get(padosiI.get(currentCellI).get(currentCellJ).get(rand)).get(padosiJ.get(currentCellI).get(currentCellJ).get(rand))==true);   
				stackI.add(currentCellI);//push current cell to stack
				stackJ.add(currentCellJ);
				// remove walls between choosen neighbour and current cell ---!OWN LOGIC NEEDED!---
				int controlI = padosiI.get(currentCellI).get(currentCellJ).get(rand) - currentCellI;
				int controlJ = padosiJ.get(currentCellI).get(currentCellJ).get(rand) - currentCellJ;
				if(controlI==0 && controlJ==1) {//neighbour on right of current cell
					maze.get(currentCellI).get(currentCellJ).set(2, 0);
					maze.get(padosiI.get(currentCellI).get(currentCellJ).get(rand)).get(padosiJ.get(currentCellI).get(currentCellJ).get(rand)).set(3, 0);
				}
				else if(controlI==0 && controlJ==-1) {//neighbour on left of current cell
					maze.get(currentCellI).get(currentCellJ).set(3, 0);
					maze.get(padosiI.get(currentCellI).get(currentCellJ).get(rand)).get(padosiJ.get(currentCellI).get(currentCellJ).get(rand)).set(2, 0);
				}
				else if(controlI==-1 && controlJ==0) {//neighbour on top of current cell
					maze.get(currentCellI).get(currentCellJ).set(0, 0);
					maze.get(padosiI.get(currentCellI).get(currentCellJ).get(rand)).get(padosiJ.get(currentCellI).get(currentCellJ).get(rand)).set(1, 0);
				}
				else if(controlI==1 && controlJ==0) {//neighbour on bottom of current cell
					maze.get(currentCellI).get(currentCellJ).set(1, 0);
					maze.get(padosiI.get(currentCellI).get(currentCellJ).get(rand)).get(padosiJ.get(currentCellI).get(currentCellJ).get(rand)).set(0, 0);
				}
				//Mark the chosen neighbour as visited and push it to the stack
				visited.get(padosiI.get(currentCellI).get(currentCellJ).get(rand)).set(padosiJ.get(currentCellI).get(currentCellJ).get(rand),true);
				stackI.add(padosiI.get(currentCellI).get(currentCellJ).get(rand));
				stackJ.add(padosiJ.get(currentCellI).get(currentCellJ).get(rand));
			}
		}
	}
	double board = 600;
	double thick = board/size;
	public void paint(Graphics g)
	{
		if(!start) {
			Initialize();
			makeMaze();
			start = true;
		}
		Graphics2D g2D = (Graphics2D) g;
		g2D.translate(frame.getWidth()/2-board/2, frame.getHeight()/2-board/2);		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    	rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
    	g2D.setRenderingHints(rh);
    	g2D.setStroke(new BasicStroke(4f));
		g2D.setColor(Color.WHITE);
		g2D.fill(new Rectangle2D.Double(0, 0, size*thick, size*thick));
		g2D.setColor(Color.BLACK);
		//g2D.draw(new Rectangle2D.Double(0, 0, size*thick, size*thick));
		g2D.setStroke(new BasicStroke(1f));
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				if(maze.get(i).get(j).get(0)==1) {//N
					g2D.draw(new Line2D.Double(j*thick, i*thick, (j+1)*thick, i*thick));
				}
				if(maze.get(i).get(j).get(1)==1) {//S
					g2D.draw(new Line2D.Double(j*thick, (i+1)*thick, (j+1)*thick, (i+1)*thick));
				}
				if(maze.get(i).get(j).get(3)==1) {//W
					g2D.draw(new Line2D.Double(j*thick, (i)*thick, (j)*thick, (i+1)*thick));
				}
				if(maze.get(i).get(j).get(2)==1) {//E
					g2D.draw(new Line2D.Double((j+1)*thick, (i)*thick, (j+1)*thick, (i+1)*thick));
				} 
			}
		}
		//g2D.setStroke(new BasicStroke(4f));
		g2D.setColor(Color.GREEN);
		g2D.fill(new Rectangle2D.Double((size-1)*thick+thick/8.000,(size-1)*thick+thick/8.000,3*thick/4.000,3*thick/4.000));
		g2D.setColor(Color.RED);
		g2D.fill(new Rectangle2D.Double(p1.x*thick+thick/8.000,p1.y*thick+thick/8.000,3*thick/4.000,3*thick/4.000));
		g2D.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g2D.drawString("Enter = RESET", 0, -10);
		if(end) {
			g2D.setColor(Color.GREEN);
			g2D.setFont(new Font("TimesRoman", Font.BOLD, 20));
			g2D.drawString("COMPLETE", (int)(size*thick/2+185), -10);
		}
		//run();
		
		repaint();
	}
	@Override
	public void run() {
		try {
			Thread.sleep(100);
			repaint();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		//if(!end) { // uncomment if you want the player to move even after completing the game
			if (e.getKeyCode()==KeyEvent.VK_UP && p1.y>0 && maze.get(p1.y).get(p1.x).get(0)!=1) {// && maze.get(p1.y-1).get(p1.x).get(1)!=1) {
        		p1.y-=1;
        	}
        	else if (e.getKeyCode()==KeyEvent.VK_DOWN && p1.y<size-1 && maze.get(p1.y).get(p1.x).get(1)!=1) {// && maze.get(p1.y+1).get(p1.x).get(0)!=1) {
        		p1.y+=1;
        	}
        	else if (e.getKeyCode()==KeyEvent.VK_RIGHT && p1.x<size-1 && maze.get(p1.y).get(p1.x).get(2)!=1) {// && maze.get(p1.y).get(p1.x+1).get(3)!=1) {
        		p1.x+=1;
        	}
        	else if (e.getKeyCode()==KeyEvent.VK_LEFT && p1.x>0 && maze.get(p1.y).get(p1.x).get(3)!=1) {// && maze.get(p1.y).get(p1.x-1).get(2)!=1) {
        		p1.x-=1;
        	}
		//}
		if (e.getKeyCode()==KeyEvent.VK_ENTER) {
			maze.clear();
			visited.clear();
			padosiI.clear();
			padosiJ.clear();
			stackI.clear();
			stackJ.clear();
			currentCellI=0;
			currentCellJ=0;
			p1.x=0;
			p1.y=0;
			start = false;
			end = false;
		}
		if(p1.x==size-1&&p1.y==size-1)
			end = true;
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		//if(arg0.getX()>0&&arg0.getY()<0) {
			//start = false;
			//Initialize();
			//makeMaze();
			//System.exit(0);
		//}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}	
}
