import java.util.*;
import java.awt.Point;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.ImmutableList;

public class ShortestPathGreedyAlgorithm {

	//==============================================================================
	public ImmutableList<EditCommand> diff(String A, String B)
	{
		return diff(ArrayUtils.toObject(A.toCharArray()), ArrayUtils.toObject(B.toCharArray()));
	}
	
	//==============================================================================
	public ImmutableList<EditCommand> diff(Object[] A, Object[] B)
	{		
		EditGraph editGraph = new EditGraph(A, B);		
		List<EditCommand> editCommands = new LinkedList<EditCommand>();
		
		NonDiagonalEdgeIterator it = new NonDiagonalEdgeIterator(editGraph);
		while(it.hasNext())
		{
			Edge nonDiagonalEdge = it.next();
			int position = nonDiagonalEdge.getEndPoint().x;
			if (nonDiagonalEdge.isVertical())
			{
				Object element = editGraph.B[nonDiagonalEdge.getEndPoint().y-1];
				editCommands.add(0, EditCommand.insert(position, element));
			}
			else
			{
				Object element = editGraph.A[nonDiagonalEdge.getEndPoint().x-1];
				editCommands.add(0, EditCommand.delete(position, element));				
			}
		}
		
		return ImmutableList.copyOf(editCommands);
	}
}

class EditGraph
{
	public Object[] A;
	public Object[] B;
	private int N;
	private int M;
	private int MAX;
	
	private Map<Integer,Integer> endpointsInDiagonalK = new HashMap<Integer, Integer>();
	private List<Map<Integer,Integer>> enpointsOfFarthestReachingDPaths = new ArrayList<Map<Integer,Integer>>();
	
	public EditGraph(Object[] A, Object[] B)
	{
		this.A = A;
		this.B = B;
		N = A.length;
		M = B.length;	
		MAX = N + M;
	}
	
	//==============================================================================
	public boolean isMatchPoint(int x, int y)
	{
		boolean isOutsideGraphBoundaries = x < 1 || x > N || y < 1 || y > M;
		if (isOutsideGraphBoundaries)
			return false;
		
		//convert to zero index
		return A[x-1].equals(B[y-1]);
	}

	//==============================================================================
	public int getLengthOfShortestEditScript()
	{
		getEnpointsOfFarthestReachingDPaths(0);
		return enpointsOfFarthestReachingDPaths.size() - 1;
	}
	
	//==============================================================================
	public int getDiagonalOfLowerRightCorner()
	{
		return N - M;
	}
	
	//==============================================================================
	public Map<Integer,Integer> getEnpointsOfFarthestReachingDPaths(int D)
	{
		boolean isCached = enpointsOfFarthestReachingDPaths.size() > 0;  
		if (isCached == false)
			findEnpointsOfFarthestReachingDPaths();
		
		return enpointsOfFarthestReachingDPaths.get(D);
	}
	
	//==============================================================================
	private List<Map<Integer,Integer>> findEnpointsOfFarthestReachingDPaths()
	{
		endpointsInDiagonalK.clear();
		endpointsInDiagonalK.put(1, 0);
		//D-path is a path starting at (0,0) that has exactly D non-diagonal edges.
		outside:for (int D = 0; D <= MAX; D++)
		{
			//We number the diagonals in the edit graph so that diagonal k consists of points (x,y) for which x - y = k 
			//A D-path end solely on odd diagonals when D is odd and even diagonals when D is even
			for (int k = -D; k <= D; k += 2)
			{				
				Point endpoint = findEndpointOfFarthestReachingDPathInDiagonalK(D, k);
				endpointsInDiagonalK.put(k, endpoint.x);
				
				if (isLongestCommonSubsequenceFound(endpoint) == true)
				{
					storeEndpointsOfDPath(endpointsInDiagonalK);
					break outside;
				}
			}
			storeEndpointsOfDPath(endpointsInDiagonalK);
		}	
		
		return enpointsOfFarthestReachingDPaths;
	}
	
	//==============================================================================
	private Point findEndpointOfFarthestReachingDPathInDiagonalK(int D, int k)
	{
		int x;
		if (k == -D || k != D && endpointsInDiagonalK.get(k-1) < endpointsInDiagonalK.get(k+1))
		{
			x = endpointsInDiagonalK.get(k+1);
		}
		else
		{
			x = endpointsInDiagonalK.get(k-1) + 1;
		}
		
		int y = x - k;

		Point result = new Point(x, y);
		traverseForwardAlongDiagonal(result);
		return result;
	}
	
	//==============================================================================
	private void traverseForwardAlongDiagonal(Point result)
	{
		while (isLongestCommonSubsequenceFound(result) == false && isMatchPoint(result.x+1, result.y+1) == true)
		{
			result.translate(1, 1);
		}
	}
	
	//==============================================================================
	private boolean isLongestCommonSubsequenceFound(Point endpoint)
	{
		return endpoint.x >= N && endpoint.y >= M;
	}
		
	//==============================================================================
	private void storeEndpointsOfDPath(Map<Integer, Integer> endpoints)
	{
		Map<Integer, Integer> copy = new HashMap<Integer, Integer>();
		copy.putAll(endpointsInDiagonalK);
		enpointsOfFarthestReachingDPaths.add(copy);		
	}

}

//==============================================================================
class NonDiagonalEdgeIterator implements Iterator<Edge>
{
	private EditGraph editGraph;
	private int d;
	private int k;
	
	
	public NonDiagonalEdgeIterator(EditGraph editGraph)
	{
		this.editGraph = editGraph;		
		d = editGraph.getLengthOfShortestEditScript();
		k = editGraph.getDiagonalOfLowerRightCorner();
	}
	
	@Override
	public boolean hasNext() {
		return d > 0;
	}

	@Override
	public Edge next() {	
		Point start = findEdgeStart();
		Point end = findEdgeEnd();
		Edge result =  new Edge(start, end);
		
		d = d - 1;
		if (result.isVertical())
			k = k + 1;
		else
			k = k - 1;
//		
//		if (result.isHorizontal())
//			k = k - 1;
//		else
//			k = k + 1;	
		
		return result;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
		
	}	
	
	private Point findEdgeEnd()
	{
		Point endpoint = getEnpointOfFarthestReachingDPathInDiagonalK(d, k);		
		Point dThNonDiagonalEdgeEnd = traverseBackwardAlongDiagonal(endpoint);
		return dThNonDiagonalEdgeEnd;
	}
	
	private Point findEdgeStart()
	{
		Map<Integer, Integer> Vd1 = editGraph.getEnpointsOfFarthestReachingDPaths(d-1);
		int x;
		int y;
		if (Vd1.containsKey(k+1))
		{
			x = Vd1.get(k+1);
			y = x - (k+1);
		}
		else
		{
			x = Vd1.get(k-1);
			y = x - (k-1);
		}
		
		
		Point dThNonDiagonalEdgeStart = new Point(x , y);
		return dThNonDiagonalEdgeStart;
	}
	
	//==============================================================================
	private Point traverseBackwardAlongDiagonal(Point endpoint)
	{
		Point result = new Point(endpoint);
		while (result.x > 0 && result.y > 0 && editGraph.isMatchPoint(result.x, result.y) == true)
			result.translate(-1, -1);
		
		return result;
	}
	
	//==============================================================================
	private Point getEnpointOfFarthestReachingDPathInDiagonalK(int d, int k)
	{
		Map<Integer, Integer> Vd = editGraph.getEnpointsOfFarthestReachingDPaths(d);
		int x = Vd.get(k);
		int y = x - k;
		return new Point(x, y);		
	}
}

class Edge
{
	private Point start;
	private Point end;
	
	Edge(Point start, Point end)
	{
		this.start = start;
		this.end = end;
	}
	
	public boolean isVertical()
	{
		return start.x == end.x;
	}
	
	public boolean isHorizontal()
	{
		return start.y == end.y;
	}
	
	public Point getEndPoint()
	{
		return new Point(end);
	}
}

