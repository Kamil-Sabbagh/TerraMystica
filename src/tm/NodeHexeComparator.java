package tm;

import java.util.Comparator;

public class NodeHexeComparator implements Comparator<NodeHexe> {

	/**
	 * COnsidering only the first two scores.
	 */
	public int compare(NodeHexe n1, NodeHexe n2) {
		if (n1.score[0] < n2.score[0])
			return -1;
		if (n1.score[0] > n2.score[0])
			return 1;
		if (n1.score[1] < n2.score[1])
			return -1;
		if (n1.score[1] > n2.score[1])
			return 1;

		return 0;
	}

}