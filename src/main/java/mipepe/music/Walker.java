package mipepe.music;

public class Walker {
	public Position position;
	public DirectionGraph[][] directionGraphs;
	public DirectionNode directionNode;
    public int graphIndex;
    public int graphIndex2;
    public int graphIterations = 16;
    public int graphIteration;
	public Walker(DirectionGraph[][] directionGraphs, int row, int column) {
		this.position = new Position(row, column);
		this.directionGraphs = directionGraphs;
		this.directionNode = directionGraphs[0][0].first;
	}
	public void moveDownOffset(int offset) {
		position = App.automaton.moveDownOffset(position, offset);
	}
	
	public void moveRightOffset(int offset) {
		position = App.automaton.moveRightOffset(position, offset);
	}
	
	public void moveUpOffset(int offset) {
		position = App.automaton.moveUpOffset(position, offset);
	}
	
	public void moveLeftOffset(int offset) {
		position = App.automaton.moveLeftOffset(position, offset);
	}
	
	public boolean move() {
		switch (directionNode.direction) {
		case DOWN:
			moveDownOffset(1);
			break;
		case RIGHT:
			moveRightOffset(1);
			break;
		case UP:
			moveUpOffset(1);
			break;
		case LEFT:
			moveLeftOffset(1);
			break;
		default:
			System.out.println("ERROR: Invalid direction...");
			System.exit(0);
		}
        if (directionNode == directionGraphs[graphIndex2][graphIndex].last) {
            graphIndex++;
            graphIndex %= directionGraphs[graphIndex2].length;
            directionNode = directionGraphs[graphIndex2][graphIndex].first;
            if (graphIndex==0) {
                graphIteration++;
                graphIteration %= graphIterations;
                if ( graphIteration == 0) {
                    //System.out.println("reset");
                    //App.reset();
                    graphIndex2++;
                    graphIndex2%= directionGraphs.length;
                    directionNode = directionGraphs[graphIndex2][graphIndex].first;
					if (graphIndex2 == 0) {
						return false;
					}
                }
            }
        } else {
		    directionNode = directionNode.next;
        }
        return true;
	}
}
