package mipepe.music;
import mipepe.music.DirectionNode.Direction;
import java.util.Iterator;
import java.util.LinkedList;
public class Automaton {
    public Cell[][] cells;
    public Cell[][] backCells;
    public LinkedList<Walker> walkers;
    public DirectionGraph[][] directionGraphs;
    public Automaton() {
        initializeCells();
        createDirectionGraph();
        int splits = 1;
        this.walkers = new LinkedList<Walker>();
        this.walkers.add(new Walker(directionGraphs, 0, 0));
    }
    public void createDirectionGraph() {
        directionGraphs = new DirectionGraph[16][16];
        for (int i = 0; i < directionGraphs.length; i++) {
            for (int j = 0; j < directionGraphs[i].length; j++) {
                directionGraphs[i][j] = new DirectionGraph(App.random, 8, 16);
            }
        }

        /*directionGraph = new DirectionGraph();
        directionGraph.addDirection(Direction.DOWN);
        directionGraph.addDirection(Direction.RIGHT);
    */
    }
    public Cell getBackCell(Position position) {
        return backCells[position.row][position.column];
    }
    public Cell getCell(Position position) {
        return cells[position.row][position.column];
    }
    public void reset() {
        for (int row = 0; row < App.height; row++) {
            for (int column = 0; column < App.width; column++) {
                cells[row][column].level = 0;
            }
        }
        /*cells[App.height/2][App.width/2].level = App.modulus-1;
        cells[App.height/2][App.width/2-1].level = App.modulus-1;
        cells[App.height/2-1][App.width/2].level = App.modulus-1;
        cells[App.height/2-1][App.width/2-1].level = App.modulus-1;
*/
        fillBackCells();
    }
    public void initializeCells() {
        cells = new Cell[App.height][App.width];
        backCells = new Cell[App.height][App.width];
        for (int row = 0; row < App.height; row++) {
            for (int column = 0; column < App.width; column++) {
                cells[row][column] = new Cell();
                backCells[row][column] = new Cell();
            }
        }
        reset();
        /*
        int prevtickModulus = 1;
        for (int row = 0; row < App.height; row++) {
            for (int column = 0; column < App.width; column++) {
                Position position = new Position(row, column);
                Cell cell = getCell(position);
                cell.tickModulus += prevtickModulus+row+column;
                cell.tickModulus %= App.tickModulusModulus;
                cell.tickModulus++;
                prevtickModulus = cell.tickModulus;
            }
        }
        */
    }
    public void tryToTickCell(Cell cell, Position position, int tickPower, boolean shoudlReplace) {
        if (shoudlReplace) {
            cell.level=tickPower;
        } else {
            cell.level+=tickPower;
        }
        cell.level %= App.modulus;
    }

    public Cell getDownCell(Position position) {
        return getCell(moveDownOffset(position,1));
    }
    public Cell getRightCell(Position position) {
        return getCell(moveRightOffset(position,1));
    }
    public Cell getLeftCell(Position position) {
        return getCell(moveLeftOffset(position,1));
    }
    public Cell getUpCell(Position position) {
        return getCell(moveUpOffset(position,1));
    }

    public Cell getDownBackCell(Position position) {
        return getBackCell(moveDownOffset(position,1));
    }
    public Cell getRightBackCell(Position position) {
        return getBackCell(moveRightOffset(position,1));
    }
    public Cell getLeftBackCell(Position position) {
        return getBackCell(moveLeftOffset(position,1));
    }
    public Cell getUpBackCell(Position position) {
        return getBackCell(moveUpOffset(position,1));
    }
    public void react(Position position) {
        Cell cell = getCell(position);
        cell.level+= getDownBackCell(position).level;
        cell.level %= App.modulus;
        cell.level+= getRightBackCell(position).level;
        cell.level %= App.modulus;
        cell.level+= getLeftBackCell(position).level;
        cell.level %= App.modulus;
        cell.level+= getUpBackCell(position).level;
        cell.level %= App.modulus;
    }
    public void iterate() {
        //fillBackCells();
        //System.out.println("iteration");
        for (Iterator<Walker> iterator = walkers.iterator(); iterator.hasNext();) {
			Walker walker = iterator.next();
			boolean shouldIterate = true;
            while (shouldIterate) {
                Cell walkerCell = getCell(walker.position);
                tryToTickCell(walkerCell, walker.position, walker.directionNode.value, true);
                //walkerCell.tickModulus();
                shouldIterate = walker.move();
            }
		}
        
        //System.out.println("second part");

        //walker.position = moveRightOffset(walker.position, walkerCell.level);
        /*fillBackCells();
        for (int row = 0; row < App.height; row++) {
            for (int column = 0; column < App.width; column++) {
                Position position = new Position(row, column);
                react(position);
            }
        }*/
    }
    public Position moveDownOffset(Position position, int offset) {
        Position moved = new Position(position.row, position.column);
		moved.row += offset;
		while (moved.row >= App.height) {
			moved.row -= App.height;
		}
        return moved;
	}

	public Position moveRightOffset(Position position, int offset) {
        Position moved = new Position(position.row, position.column);
		moved.column += offset;
		while (moved.column >= App.width) {
			moved.column -= App.width;
		}
        return moved;
	}

	public Position moveUpOffset(Position position, int offset) {
        Position moved = new Position(position.row, position.column);
		moved.row -= offset;
		while (moved.row < 0) {
			moved.row += App.height;
		}
        return moved;	
	}

	public Position moveLeftOffset(Position position, int offset) {
        Position moved = new Position(position.row, position.column);
		moved.column -= offset;
		while (moved.column < 0) {
			moved.column += App.width;
		}
        return moved;
	}
    public void fillBackCells() {
        for (int row = 0; row < App.height; row++) {
            for (int column = 0; column < App.width; column++) {
                backCells[row][column].level = cells[row][column].level;
            }
        }
    }
}