
/*Ankur Sharma : axs178532*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class ID3AlgoBonus {

	List<String> atrributes = new ArrayList<String>();
	List<String> atrributesaccuracy = new ArrayList<String>();
	int tempval = -1;

	List<String[]> datainattribute = new ArrayList<String[]>();
	List<String[]> datainattributeforacc = new ArrayList<String[]>();
	List<String[]> datainattributeforaccval = new ArrayList<String[]>();
	List<String[]> datainattributeforacctest = new ArrayList<String[]>();

	List<String[]> datainattributerandom = new ArrayList<String[]>();

	List<String[]> datainattributeforaccrandom = new ArrayList<String[]>();
	List<String[]> datainattributeforaccvalrandom = new ArrayList<String[]>();
	List<String[]> datainattributeforacctestrandom = new ArrayList<String[]>();

	List<String[]> accuracyList = new ArrayList<String[]>();

	List<String> instances = new ArrayList<String>();
	// ArrayList<String> NodeUsed = new ArrayList<String>();
	ArrayList<String> PruneNodeList = new ArrayList<String>();
	ArrayList<String> PruneNodeListAfter = new ArrayList<String>();
	int count = 0;
	int depthval = 0;
	int accuracycount = 0;

	ArrayList<String> NodeUsedright = new ArrayList<String>();
	ArrayList<String> NodeUsedleft = new ArrayList<String>();

	private Map<Integer, String> header = new HashMap<Integer, String>();
	private Map<String, Integer> header_reverse_map = new HashMap<String, Integer>();
	int nodecount = 0;
	int leafnodecount = 0;
	int purenodes = 0;
	TreeNode root = new TreeNode();
	TreeNode rootval = new TreeNode();
	TreeNode roottest = new TreeNode();

	TreeNode rootrandom = new TreeNode();
	// TreeNode rootvalrandom = new TreeNode();
	// TreeNode roottestrandom = new TreeNode();

	public void readDataString(String FileName) throws Exception {

		String csvFile = FileName;
		String cvsSplitBy = ",";

		BufferedReader br = new BufferedReader(new FileReader(csvFile));
		String instance = null;
		while ((instance = br.readLine()) != null) {

			instances.add(instance);
		}
		br.close();

		atrributes.addAll(Arrays.asList(instances.get(0).split(",")));
		atrributes.remove(atrributes.size() - 1);
		atrributesaccuracy = atrributes;

		for (int i = 1; i < instances.size(); i++) {
			datainattribute.add(instances.get(i).split(","));
		}
		for (int i = 1; i < instances.size() - 1; i++) {
			datainattributeforacc.add(instances.get(i).split(","));
		}

	}

	public void readDataStringacc(String FileName) throws Exception {

		String csvFile = FileName;
		String cvsSplitBy = ",";

		BufferedReader br = new BufferedReader(new FileReader(csvFile));
		String instance = null;
		while ((instance = br.readLine()) != null) {

			instances.add(instance);
		}
		br.close();

		for (int i = 1; i < instances.size() - 1; i++) {
			datainattributeforaccval.add(instances.get(i).split(","));
		}

	}

	public void readDataStringacctest(String FileName) throws Exception {

		String csvFile = FileName;
		String cvsSplitBy = ",";

		BufferedReader br = new BufferedReader(new FileReader(csvFile));
		String instance = null;
		while ((instance = br.readLine()) != null) {

			instances.add(instance);
		}
		br.close();

		for (int i = 1; i < instances.size() - 1; i++) {
			datainattributeforacctest.add(instances.get(i).split(","));
		}

	}

	///////////// for random selection //////////////

	public void readDataStringrandom(String FileName) throws Exception {

		String csvFile = FileName;
		String cvsSplitBy = ",";

		BufferedReader br = new BufferedReader(new FileReader(csvFile));
		String instance = null;
		while ((instance = br.readLine()) != null) {

			instances.add(instance);
		}
		br.close();

		atrributes.addAll(Arrays.asList(instances.get(0).split(",")));
		atrributes.remove(atrributes.size() - 1);
		atrributesaccuracy = atrributes;

		for (int i = 1; i < instances.size(); i++) {
			datainattributerandom.add(instances.get(i).split(","));
		}
		for (int i = 1; i < instances.size() - 1; i++) {
			datainattributeforaccrandom.add(instances.get(i).split(","));
		}

	}

	public void readDataStringaccrandom(String FileName) throws Exception {

		String csvFile = FileName;
		String cvsSplitBy = ",";

		BufferedReader br = new BufferedReader(new FileReader(csvFile));
		String instance = null;
		while ((instance = br.readLine()) != null) {

			instances.add(instance);
		}
		br.close();

		for (int i = 1; i < instances.size() - 1; i++) {
			datainattributeforaccvalrandom.add(instances.get(i).split(","));
		}

	}

	public void readDataStringacctestrandom(String FileName) throws Exception {

		String csvFile = FileName;
		String cvsSplitBy = ",";

		BufferedReader br = new BufferedReader(new FileReader(csvFile));
		String instance = null;
		while ((instance = br.readLine()) != null) {

			instances.add(instance);
		}
		br.close();

		for (int i = 1; i < instances.size() - 1; i++) {
			datainattributeforacctestrandom.add(instances.get(i).split(","));
		}

	}

	//////////////////////////////////////////////////

	// Creating the root node with complete data set
	public void createRoot(TreeNode root, int arg) throws Exception {

		int positivecounter = 0;
		int negativecounter = 0;

		// System.out.println("arr size: "+ datainattribute.size());

		for (String[] datainattribute : datainattribute) {

			String lastNum = datainattribute[datainattribute.length - 1];
			if (lastNum.equals("1")) {
				positivecounter++;
			} else if (lastNum.equals("0")) {
				negativecounter++;
			}
		}

		root.positiveSamplesCnt = positivecounter;
		root.negativeSamplesCnt = negativecounter;
		root.dataSet = datainattribute;

		root.attributelist = atrributes;
		root.entropy = getEntropy(negativecounter, positivecounter, (negativecounter + positivecounter));
		root.depth = 0;
		root.parent = null;

		createNode(root, arg);
	}

	/////// for random ///////

	public void createRootrandom(TreeNode root, int arg) throws Exception {

		int positivecounter = 0;
		int negativecounter = 0;

		// System.out.println("arr size: "+ datainattribute.size());

		for (String[] datainattribute : datainattributerandom) {

			String lastNum = datainattribute[datainattribute.length - 1];
			if (lastNum.equals("1")) {
				positivecounter++;
			} else if (lastNum.equals("0")) {
				negativecounter++;
			}
		}
		root.positiveSamplesCnt = positivecounter;
		root.negativeSamplesCnt = negativecounter;
		root.dataSet = datainattributeforaccrandom;

		root.attributelist = atrributes;
		root.entropy = getEntropy(negativecounter, positivecounter, (negativecounter + positivecounter));
		root.depth = 0;
		root.parent = null;

		createNode(root, arg);
	}

	public String chooseBestNode(TreeNode node, List<String[]> parentset, List<String> attributelist, int pos, int neg)
			throws Exception {

		double entMin = 2.0;
		String SplitNode = "";
		// System.out.println("datainattribute.size(): "+ datainattribute.size());
		for (int k = 0, i = 0; k < attributelist.size() && i < datainattribute.size(); k++, i++) {

			int positivecounterright = 0;
			int negativecounterright = 0;
			int positivecounterleft = 0;
			int negativecounterleft = 0;
			for (String[] parentdata : parentset) {

				String label = parentdata[parentdata.length - 1];
				// System.out.println("label: "+ label);
				// System.out.println("parentdata[i].: "+ parentdata[i]);

				if (parentdata[i].equals("1")) {
					if (label.equals("1")) {
						positivecounterright++;
					} else if (label.equals("0")) {
						negativecounterright++;
					}
				} else if (parentdata[i].equals("0")) {
					if (label.equals("1")) {
						positivecounterleft++;
					} else if (label.equals("0")) {
						negativecounterleft++;
					}
				}

			}

			double entropyright = getEntropy(negativecounterright, positivecounterright,
					(positivecounterright + negativecounterright));
			double entropyleft = getEntropy(negativecounterleft, positivecounterleft,
					(positivecounterleft + negativecounterleft));

			double weightedaEnt = ((double) (positivecounterright + negativecounterright) / (pos + neg)) * entropyright
					+ ((double) (positivecounterleft + negativecounterleft) / (pos + neg)) * entropyleft;

			if (weightedaEnt <= entMin && !(node.usedAttribute).contains(node.attributelist.get(k))) {
				entMin = weightedaEnt;
				// System.out.println("eeeeeeeee :"+ entMin);
				SplitNode = attributelist.get(k);
			}

		}

		return SplitNode;
	}

	private Random random = new Random();
	String randomnode = "";
	int index = -1;

	public String chooseRandomNode(TreeNode node) {

		Random rand = new Random();

		int randomNum = rand.nextInt((19 - 0) + 1) + 0;

		return atrributesaccuracy.get(randomNum);

	}

	public void createNode(TreeNode node, int arg) throws Exception {

		if (node.negativeSamplesCnt == 0 || node.positiveSamplesCnt == 0) {
			// System.out.println("inside pure node");
			if (node.negativeSamplesCnt == 0) {
				node.label = 0;
				node.finalattribute = 1;

			} else if (node.positiveSamplesCnt == 0) {
				node.label = 1;
				node.finalattribute = 0;

			}
			purenodes++;

		} else {

			double Parententropy = node.entropy;
			// Finding the splitting element

			List<String[]> parentset = new ArrayList<String[]>();

			parentset = node.dataSet;
			List<String> attList = new ArrayList<String>();
			for (String s : node.attributelist) {
				attList.add(s);
			}

			String nodeSplit = "";

			// ID3 case

			if (arg == 1) {
				nodeSplit = chooseBestNode(node, parentset, attList, node.positiveSamplesCnt, node.negativeSamplesCnt);
			}

			// Random case
			if (arg == 2) {
				String nodereturend = chooseRandomNode(node);

				while (node.usedAttribute.contains(nodereturend)) {
					nodereturend = chooseRandomNode(node);
				}

				nodeSplit = nodereturend.trim();
			}

			// node.usedAttribute.add(nodeSplit);
			if (nodeSplit != "") {

				// for left child
				List<String[]> parentsetleft = new ArrayList<String[]>();

				// for right child
				List<String[]> parentsetright = new ArrayList<String[]>();

				int indexOfSplitNode = node.attributelist.indexOf(nodeSplit);
				// System.out.println("nodeSplit: "+ nodeSplit+" indexOfSplitNode :"+
				// indexOfSplitNode);
				// System.out.println("index of split attribute:"+ indexOfSplitNode );
				TreeNode leftchild = new TreeNode();
				TreeNode rightchild = new TreeNode();
				int leftnegcount = 0, leftposcount = 0, rightnegcount = 0, rightposcount = 0;

				for (String[] parentdata : parentset) {

					if (parentdata[indexOfSplitNode].equals("0")) {
						leftchild.dataSet.add(parentdata);
						if (parentdata[parentdata.length - 1].equals("0")) {
							leftnegcount++;
						}
						if (parentdata[parentdata.length - 1].equals("1")) {
							leftposcount++;
						}

					} else if (parentdata[indexOfSplitNode].equals("1")) {
						rightchild.dataSet.add(parentdata);
						if (parentdata[parentdata.length - 1].equals("0")) {
							rightnegcount++;
						}
						if (parentdata[parentdata.length - 1].equals("1")) {
							rightposcount++;
						}

					}

				}
				leftchild.negativeSamplesCnt = leftnegcount;
				leftchild.positiveSamplesCnt = leftposcount;
				rightchild.negativeSamplesCnt = rightnegcount;
				rightchild.positiveSamplesCnt = rightposcount;

				leftchild.attributelist = attList;
				rightchild.attributelist = attList;
				leftchild.usedAttribute.addAll(node.usedAttribute);

				leftchild.usedAttribute.add(nodeSplit);
				rightchild.usedAttribute.add(nodeSplit);
				rightchild.usedAttribute.addAll(node.usedAttribute);

				leftchild.entropy = getEntropy(leftnegcount, leftposcount, leftnegcount + leftposcount);
				rightchild.entropy = getEntropy(rightnegcount, rightposcount, rightnegcount + rightposcount);

				leftchild.depth = node.depth + 1;
				rightchild.depth = node.depth + 1;

				leftchild.label = 0;
				rightchild.label = 1;

				leftchild.parent = node;
				rightchild.parent = node;

				leftchild.splitAttribute = nodeSplit;
				rightchild.splitAttribute = nodeSplit;

				node.nodeatt = nodeSplit;

				node.leftchild = leftchild;
				node.rightchild = rightchild;
				// DisplayTree(node);

				createNode(node.leftchild, arg);

				createNode(node.rightchild, arg);

			} else {
				if (node.positiveSamplesCnt > node.negativeSamplesCnt) {
					node.label = 1;
				}
				if (node.positiveSamplesCnt < node.negativeSamplesCnt) {
					node.label = 0;
				}
				// node.label = node.positiveSamplesCnt + node.negativeSamplesCnt;
			}

		}

	}

	private double getEntropy(int negativeSamplesCnt, int positiveSamplesCnt, int totalSamplesCnt) throws Exception {

		if (totalSamplesCnt == 0)
			return 0;

		double en_new = (negativeSamplesCnt == 0 ? 0
				: -(((double) negativeSamplesCnt / totalSamplesCnt)
						* Math.log10((double) negativeSamplesCnt / totalSamplesCnt) / Math.log10(2)))
				+ (positiveSamplesCnt == 0 ? 0
						: -(((double) positiveSamplesCnt / totalSamplesCnt)
								* Math.log10((double) positiveSamplesCnt / totalSamplesCnt) / Math.log10(2)));

		return en_new;
	}

	public void DisplayTree(TreeNode node) {
		HashMap<Integer, String> btree = new HashMap<Integer, String>();
		if (node.splitAttribute != null) {

			for (int i = 1; i < node.depth; i++) {
				System.out.print("| ");
				if (node.depth > depthval)
					depthval = node.depth;
			}
			if (node.finalattribute == 2) {
				System.out.println(node.splitAttribute + " " + node.label);
				nodecount++;
			} else {
				System.out.println(node.splitAttribute + " " + node.label + ":" + node.finalattribute);
				leafnodecount++;
				nodecount++;

			}

			PruneNodeList.add(node.splitAttribute);
		}

		// System.out.println("last att: "+ node.splitAttribute);
	}

	public static void findnode(TreeNode node, String fetchednode) {
		System.out.println("node: " + node.splitAttribute);
		// System.out.println("node.rightchild : "+ node.rightchild);

		if (null == node.rightchild && node.splitAttribute == fetchednode) {
			System.out.println("true : ");

		} else {
			findnode(node.rightchild, fetchednode);
		}
	}

	public void BTDisplay(TreeNode node) {
		if (node == null) {
			return;
		}
		for (int i = 1; i < node.depth; i++) {
			System.out.print("| ");

		}
		if (node.finalattribute == 2 && node.splitAttribute != null) {
			System.out.println(node.splitAttribute + " " + node.label);
			nodecount++;

		} else if (node.splitAttribute != null) {
			System.out.println(node.splitAttribute + " " + node.label + ":" + node.finalattribute);
			nodecount++;
			leafnodecount++;
		}
		BTDisplay(node.leftchild);
		BTDisplay(node.rightchild);

	}

	public void BTDisplaycount(TreeNode node) {
		if (node == null) {
			return;
		}

		if (node.finalattribute == 2 && node.splitAttribute != null) {
			nodecount++;

		} else if (node.splitAttribute != null) {
			nodecount++;
			leafnodecount++;
		}
		BTDisplaycount(node.leftchild);
		BTDisplaycount(node.rightchild);

	}

	boolean checkLeaf(TreeNode node) {
		if (node == null)
			return false;
		if (node.leftchild == null && node.rightchild == null)
			return true;
		return false;
	}

	public int calculateSumOfDepthNodes(TreeNode node) {
		int sum = 0;
		if (node != null) {
			if (checkLeaf(node.leftchild))
				sum += node.leftchild.depth;
			else
				sum += calculateSumOfDepthNodes(node.leftchild);

			sum += calculateSumOfDepthNodes(node.rightchild);
		}

		return sum;

	}
	// todo

	public int accuracycal(TreeNode node, String[] data, List<String> att) {
		int ret = -1;

		if (node.leftchild != null || node.rightchild != null) {
			// System.out.println("att : "+ att.get(0));
			String splitatt = node.nodeatt;
			// System.out.println(splitatt);
			int index = -1;
			for (int i = 0; i < att.size(); i++) {
				if (att.get(i).equals(splitatt)) {
					index = i;
					break;
				}
			}

			if (index != -1) {
				// System.out.println("data[index]: "+ data[index]);
				int dataval = Integer.parseInt(data[index]);
				if (dataval == 1) {
					ret = accuracycal(node.rightchild, data, att);
				} else if (dataval == 0) {
					ret = accuracycal(node.leftchild, data, att);

				}
				return ret;
			}
		} else {
			return node.finalattribute;
		}
		return 0;

	}

	public static void main(String args[]) throws Exception {
		ID3AlgoBonus obj = new ID3AlgoBonus();

		obj.nodecount = 0;
		obj.leafnodecount = 0;
		System.out.println("\n\n---------------------Accuracy calculation with ID3--------------------------");

		// for training set:
		try {
			obj.readDataString(args[0].trim());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			obj.createRoot(obj.root, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		obj.BTDisplaycount(obj.root);

		System.out.println("\n\nAccuracy: Training set");
		System.out.println("------------------------");

		System.out.println("Number of training instances: " + (obj.instances.size() - 1));
		System.out.println("Number of training attributes: " + obj.atrributes.size());
		System.out.println("Total Number of nodes in the tree: " + obj.nodecount);
		System.out.println("Number of leaf nodesin the tree: " + obj.leafnodecount);
		// obj.accuracycal2(obj.root,obj.datainattributeforacc ,obj.atrributes);
		int acccount = 0;
		int negcount = 0;
		for (int i = 0; i < obj.datainattributeforacc.size(); i++) {
			// System.err.println(obj.datainattributeforacc.get(i));
			int accval = obj.accuracycal(obj.root, obj.datainattributeforacc.get(i), obj.atrributes);

			if (accval == Integer
					.parseInt(obj.datainattributeforacc.get(i)[obj.datainattributeforacc.get(i).length - 1])) {

				acccount++;
			} else {
				negcount++;
			}
		}
		// System.out.println("value :"+acccount);

		System.out.println("Accuracy of the model on the Training dataset : "
				+ ((double) acccount / (double) (acccount + negcount)) * 100 + " %");

		int sum = obj.calculateSumOfDepthNodes(obj.root);
		double avgID3 = (double) sum / (double) obj.leafnodecount;
		int nodesID3 = obj.nodecount;
		System.out.println("Average Depth: " + avgID3);

		obj.datainattributeforacc.clear();
		obj.instances.clear();
		acccount = 0;
		negcount = 0;
		obj.readDataStringacc(args[1].trim());
		for (int i = 0; i < obj.datainattributeforaccval.size(); i++) {
			// System.err.println(obj.datainattributeforacc.get(i));
			int accval = obj.accuracycal(obj.root, obj.datainattributeforaccval.get(i), obj.atrributes);
			if (accval == Integer
					.parseInt(obj.datainattributeforaccval.get(i)[obj.datainattributeforaccval.get(i).length - 1])) {

				acccount++;
			} else {
				negcount++;
			}
		}
		System.out.println("\nAccuracy of the model on the Validation dataset : "
				+ ((double) acccount / (double) (acccount + negcount)) * 100 + " %");

		obj.datainattributeforacc.clear();
		obj.datainattributeforaccval.clear();
		obj.instances.clear();
		acccount = 0;
		negcount = 0;
		obj.readDataStringacctest(args[2].trim());
		for (int i = 0; i < obj.datainattributeforacctest.size(); i++) {
			// System.err.println(obj.datainattributeforacc.get(i));
			int accval = obj.accuracycal(obj.root, obj.datainattributeforacctest.get(i), obj.atrributes);
			if (accval == Integer
					.parseInt(obj.datainattributeforacctest.get(i)[obj.datainattributeforacctest.get(i).length - 1])) {

				acccount++;
			} else {
				negcount++;
			}
		}
		System.out.println("\nAccuracy of the model on the Test dataset : "
				+ ((double) acccount / (double) (acccount + negcount)) * 100 + " %");

		obj.count = 0;
		obj.nodecount = 0;
		obj.leafnodecount = 0;
		obj.purenodes = 0;
		obj.datainattribute.clear();
		obj.atrributes.clear();
		obj.instances.clear();
		obj.accuracyList.clear();
		obj.atrributesaccuracy.clear();
		obj.datainattributeforacc.clear();
		obj.datainattributeforacctest.clear();
		obj.datainattributeforaccval.clear();

		System.out.println(
				"\n\n---------------------Accuracy Calculation with Random Function--------------------------");

		obj.nodecount = 0;
		obj.leafnodecount = 0;

		// for training set:
		try {
			obj.readDataStringrandom(args[0].trim());

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			obj.createRootrandom(obj.rootrandom, 2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		obj.BTDisplaycount(obj.rootrandom);

		System.out.println("\n---- Accuracy of the Training Set------");

		System.out.println("Number of training instances: " + (obj.instances.size() - 1));
		System.out.println("Number of training attributes: " + obj.atrributes.size());
		System.out.println("Total Number of nodes in the tree: " + obj.nodecount);
		System.out.println("Number of leaf nodesin the tree: " + obj.leafnodecount);
		// obj.accuracycal2(obj.root,obj.datainattributeforacc ,obj.atrributes);
		acccount = 0;
		negcount = 0;
		for (int i = 0; i < obj.datainattributeforaccrandom.size(); i++) {
			int accval = obj.accuracycal(obj.rootrandom, obj.datainattributeforaccrandom.get(i), obj.atrributes);
			// System.out.println("accval: "+ accval + " class: "+ Integer.parseInt(
			// obj.datainattributeforaccrandom.get(i)[obj.datainattributeforaccrandom.get(i).length
			// - 1]));
			if (accval == Integer.parseInt(
					obj.datainattributeforaccrandom.get(i)[obj.datainattributeforaccrandom.get(i).length - 1])) {

				acccount++;
			} else {
				negcount++;
			}
		}

		System.out.println("Accuracy of the model on the Training dataset : "
				+ ((double) acccount / (double) (acccount + negcount)) * 100 + " %");

		int sumrandom = obj.calculateSumOfDepthNodes(obj.rootrandom);
		double avgRandom = (double) sumrandom / (double) obj.leafnodecount;
		int nodesRandom = obj.nodecount;

		System.out.println("Average Depth: " + avgRandom);

		System.out.println("\n---- Accuracy of the Validation Set------");

		obj.count = 0;
		obj.nodecount = 0;
		obj.leafnodecount = 0;
		obj.purenodes = 0;
		obj.datainattribute.clear();
		// obj.atrributes.clear();
		obj.instances.clear();
		// obj.accuracyList.clear();
		// obj.atrributesaccuracy.clear();
		obj.datainattributeforacc.clear();
		obj.datainattributeforacctest.clear();
		obj.datainattributeforaccval.clear();
		obj.datainattributeforaccrandom.clear();

		obj.nodecount = 0;
		obj.leafnodecount = 0;

		obj.readDataStringaccrandom(args[1].trim());
		acccount = 0;
		negcount = 0;
		for (int i = 0; i < obj.datainattributeforaccvalrandom.size(); i++) {
			int accval = obj.accuracycal(obj.rootrandom, obj.datainattributeforaccvalrandom.get(i), obj.atrributes);

			if (accval == Integer.parseInt(
					obj.datainattributeforaccvalrandom.get(i)[obj.datainattributeforaccvalrandom.get(i).length - 1])) {

				acccount++;
			} else {
				negcount++;
			}
		}

		System.out.println("Accuracy of the model on the Validation dataset : "
				+ ((double) acccount / (double) (acccount + negcount)) * 100 + " %");

		System.out.println("\n---- Accuracy of the Test Set------");

		obj.count = 0;
		obj.nodecount = 0;
		obj.leafnodecount = 0;
		obj.purenodes = 0;
		obj.datainattribute.clear();
		// obj.atrributes.clear();
		obj.instances.clear();
		// obj.accuracyList.clear();
		// obj.atrributesaccuracy.clear();
		obj.datainattributeforacc.clear();
		obj.datainattributeforacctest.clear();
		obj.datainattributeforaccval.clear();
		obj.datainattributeforaccvalrandom.clear();
		obj.nodecount = 0;
		obj.leafnodecount = 0;

		obj.readDataStringacctestrandom(args[2].trim());

		acccount = 0;
		negcount = 0;
		for (int i = 0; i < obj.datainattributeforacctestrandom.size(); i++) {
			int accval = obj.accuracycal(obj.rootrandom, obj.datainattributeforacctestrandom.get(i), obj.atrributes);

			if (accval == Integer.parseInt(obj.datainattributeforacctestrandom
					.get(i)[obj.datainattributeforacctestrandom.get(i).length - 1])) {

				acccount++;
			} else {
				negcount++;
			}
		}

		System.out.println("Accuracy of the model on the Test dataset : "
				+ ((double) acccount / (double) (acccount + negcount)) * 100 + " %");

	}

}