import java.util.ArrayList;
import java.util.List;

public class TreeNode {

	public double entropy;
	public int negativeSamplesCnt;
	public int positiveSamplesCnt;
	public int totalSamplesCnt;
	public List<String> usedAttribute = new ArrayList<String>();
	public int label;
	public List<String[]> dataSet = new ArrayList<String[]>();;
	public List<String> attributelist = new ArrayList<String>();
	public String splitAttribute;
	public int depth;
	TreeNode leftchild;
	TreeNode rightchild;
	TreeNode parent;
	public int finalattribute = 2;
	public String nodeatt;
}
