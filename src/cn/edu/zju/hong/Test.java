package cn.edu.zju.hong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.List;
import java.util.TreeSet;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableTreeItem;
import org.eclipse.swt.widgets.*;

import cn.edu.zju.lda.Corpus;
import cn.edu.zju.lda.LdaGibbsSampler;
import cn.edu.zju.lda.LdaUtil;

import org.eclipse.swt.custom.TableTree;


/**
 * @author hty
 *
 */
@SuppressWarnings("deprecation")
public class Test {

	/**
	 * The windows of the SWT program. <br>
	 * 程序框体
	 */
	protected Shell shell;
	
	/**
	 * The menu item to open a new xml file<br>
	 * 打开新的XML文件的菜单选项
	 */
	private MenuItem mntmOpenNew; 
	
	/** 
	 * The menu item to choose lda param <br>
	 * 进入LDA参数选择界面的菜单选项
	 */
	private MenuItem mntmLdaNew; 
	
	/**
	 * The menu item to seperate the words <br>
	 * 分词并去除停词的菜单选项
	 */
	private MenuItem mntmSegWord;
	
	/**
	 * The menu item to get the page getting the best answer of the newly entered question <br>
	 * 进入输入新问题并进行最佳答案推荐的界面的菜单选项
	 */
	private MenuItem mntmGetAnswer;
	
	/**
	 * The menu item to get the precision of the recommended answer<br>
	 * 进入显示推荐答案准确率的界面的菜单选项
	 */
	private MenuItem mntmGetAccuracy;
	
	/**
	 * The page to choose the input file<br>
	 * 打开文件选项
	 */
	private FileDialog dialog;
	
	/**
	 * Getting the path of the input file<br>
	 * 输入XML文件路径
	 */
	private String[] filePath; 
		
	private String filename;
	
	/**
	 * The words got from the typein question<br>
	 * 从文本框中获取的文字
	 */
	private String questionContent;
	
	/**
	 * The words being segged from the typein question<br>
	 * 经过分词并去除停词后的问题，是一组words的List，用于获得最佳答案
	 */
	private List<String> segQuestionWords1;
	
	/**
	 * The words being segged from the existing question<br>
	 * 经过分词并去除停词后的问题，是一组words的List，用于获得正确率
	 */
	private List<String> segQuestionWords2;
	
	/**
	 * XML解析类的实例
	 */
	private XMLReader xmlReader;
	
	/**
	 * 分词及停词去除类的实例
	 */
	private SegWords segWords;
	
	/**
	 * 主界面的标题
	 */
	private Label labelTitle;
	
	/**
	 * LDA参数选择界面的标题
	 */
	private Label labelLdaTitle; 
	
	/**
	 * 选择进行LDA处理的内容的说明注释
	 */
	private Label labelLdaContent;
	
	/**
	 * 选择LDA的主题数目的说明注释
	 */
	private Label labelTopicNum;
	
	/**
	 * LDA模型的图片
	 */
	private Label labelModelPicture;
	
	/**
	 * LDA结果显示界面的标题
	 */
	private Label labelLdaResult;
	
	/**
	 * 通过LDA进行答案预测的界面的标题
	 */
	private Label labelGetAnswerTitle; 
	
	/**
	 * 答案预测界面中输入问题的注释说明
	 */
	private Label labelQuestionIn;
	
	/**
	 * 答案预测界面中显示推荐问题的注释说明
	 */
	private Label labelAnswerOut;
	
	/**
	 * XML中的标题的逐条显示的可滑动界面
	 */
	private ScrolledComposite sc1;
	
	/**
	 * 点击sc1中的一条标题显示具体内容的可滑动界面
	 */
	private ScrolledComposite sc2;
	
	/**
	 * 显示分词并去除停词的可滑动界面
	 */
	private ScrolledComposite sc3;
	
	/**
	 * 显示LDA结果的可滑动界面
	 */
	private ScrolledComposite sc4;
	
	/**
	 * 输入问题的可滑动界面
	 */
	private ScrolledComposite sc5;
	
	/**
	 * 显示推荐答案的可滑动界面
	 */
	private ScrolledComposite sc6;
		
	
	/**
	 * sc1中
	 */
	private Composite compositeTitle;
	
	/**
	 * sc2中
	 */
	private Composite compositeContent;
	
	/**
	 * sc3中
	 */
	private Composite compositeSegWord;
	
	/**
	 * sc4中
	 */
	private Composite compositeLdaDisplay;
	
	/**
	 * sc5中
	 */
	private Composite compositeQuestionIn;
	
	/**
	 * sc6中
	 */
	private Composite compositeAnswerOut;
		
	private Text lastPageUp,nextPageUp,lastPageDown,nextPageDown,textUp1,textDown1,goUp,goDown,textUp2,textDown2,space;
	
	/**
	 * 显示每条XML文档具体内容的文字框
	 */
	private Text textContent;
	
	/**
	 * 显示分词并去除停词后的文字框
	 */
	private Text textContentSegged;
	
	/**
	 * 显示LDA结果的文字框
	 */
	private Text textContentLda;
	
	/**
	 * 输入问题的文字框
	 */
	private Text textQuestionIn;
	
	/**
	 * 显示推荐问题的文字框
	 */
	private Text textAnswerOut;
	
	private int titleSize,page,pageAll,chosen;
	private Button btnGoUp,btngoDown;
	
	/**
	 * LDA参数选择完成后的确认键
	 */
	private Button ldaChooseButton;
	
	/**
	 * 返回LDA参数选择界面的按键
	 */
	private Button returnLdaChooseButton;
	
	/**
	 * 从lda参数选择界面返回主界面的按键
	 */
	private Button returnMainButton1;
	
	/**
	 * 从最佳答案推荐界面返回主界面的按键
	 */
	private Button returnMainButton2;
	
	/**
	 * 获得推荐答案的按键
	 */
	private Button getAnswerButton;
		
	private Text[] textTitle=new Text[NUMBER_PER_PAGE];	
	
	/**
	 * 绘制点击框类的实例
	 */
	private CustomPaintListener listener1;
	
	/**
	 * 每页显示的XML条数
	 */
	private static final int NUMBER_PER_PAGE=20;
	
	/**
	 * 全局变量，用来确认是否已经进行过分词和去除停词处理
	 */
	private boolean ifSegged = false;
	
	/**
	 * LDA类型的combo选项
	 */
	private Combo comboLDAContent;
	
	/**
	 * LDA主题数目的combo选项
	 */
	private Combo comboTopicNum;
	
	/**
	 * LDA的topic数
	 */	
	private int topicNumber = 0;
	
	/**
	 * LDA类型<br>
	 * 0 是只对question进行LDA<br>
	 * 1 是只对answer进行LDA<br>
	 * 2 是对question和answer一起进行LDA<br>
	 */
	private int	ldaChooseType = -1;
	
	/**
	 * 文档集的类的实例
	 */
	private Corpus corpus;
	
	/**
	 * GibbsSampler类的实例
	 */
	private LdaGibbsSampler ldaGibbsSampler; 
	
	/**
	 * topic的一个数组<br>
	 * 每个都是一个Map, Map中的每个元素都是top possible的词语和对应的概率
	 */
	private Map<String, Double>[] topicMap;
	
	/**
	 * 储存lda结果的数组，每个是一个topic下的所有top words的word和p
	 */
	private ArrayList<String> ldaResult;
	
	/**
	 * 存储着lda结果中每个topic的top words，用于tableTree中
	 */
	private ArrayList<String> ldaResultWord;
	
	/**
	 * 分好词后的所有回答，数组的每个内容是每条回答的分好词后的内容（含空格）
	 */
	private ArrayList<String> segAnswerAll;
	
	/**
	 * 所有的回答，数组的你每个都是一个回答
	 */
	private ArrayList<String> answerAll;
	
	/**
	 * tableTree类，用来显示lda的结果
	 */
	private TableTree tableTreeLdaResult;
	
	/**
	 * tableTree中的table
	 */
	private Table tableLdaResult;
	
	/**
	 * lda显示的table的每栏的宽度
	 */
	private final int[] LdaResultColunmsWidth = {100,200,200};
	
	/**
	 * lda结果中的word对应的出现概率
	 */
	private ArrayList<Double> ldaResultValue;
	
	/**
	 * tableTree 中的父类
	 */
	private TableTreeItem parentLdaResult;
	
	/**
	 * tableTree 中的子类
	 */
	private TableTreeItem childLdaResult;
	
	/**
	 * 界面中的edit menu
	 */
	private MenuItem mntmEdit;
	
	private Menu menu_1;
	
	/**
	 * 最佳答案的index
	 */
	private int BestAnswerIndex=-1;
	
	/**
	 * 目前暂定的最佳的主题数
	 */	
	private final int BestTopicNum = 10;

	/**
	 * 主题与词的分布 <br>
	 * phiAnswer[k][w] <br>
	 * k 是topic， w是词
	 */
	private double[][] phiAnswer;
	
	/**
	 * 文档和主题的分布<br>
	 * thetaAnswer[m][k]
	 */
	private double[][] thetaAnswer;
	
	/**
	 * 判断是否已经采样过
	 */
	private boolean ifSampled = false;
	
	/**
	 * 判断是否已经经过正确率计算
	 */
	private boolean ifGetAccuracy = false;
	
	/**
	 * 推荐答案的正确率
	 */
	private double precision;
	
	/**
	 * 正确答案的数目，用于计算正确率中
	 */
	private int correctAnswer = 0;
	
	/**
	 * 总的文档数目,用于计算正确率中
	 */
	private int totalDoc;
	
	/**
	 * 问题数目,用于读入XML后的细节展示
	 */
	private int questionNum;
	
	/**
	 * 回答数目，用于读入XML后的细节展示
	 */
	private int answerNum;
	
	/**
	 * 字符数目，用于读入XML后的细节展示
	 */
	private int characterNum = 0;
	
	/**
	 * 分词结束后显示在messageBox中的内容
	 */
	private String msgSeg = "";
	
	/**
	 * 有超过一条回复的文档数目，用于计算precision
	 */
	private int docMoreAns2;
	
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Test window = new Test();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}		
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.CLOSE | SWT.MIN);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		shell.setSize(1024,768);
		shell.setMinimumSize(1024,768);
		shell.setText("SWT Application");
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");
		
		Menu menu1 = new Menu(mntmFile);
		mntmFile.setMenu(menu1);
		
		mntmOpenNew = new MenuItem(menu1,SWT.NONE);
		mntmOpenNew.setText("Open xml");
		mntmOpenNew.setAccelerator(SWT.F2);
		mntmOpenNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				dialog = new org.eclipse.swt.widgets.FileDialog(shell, SWT.OPEN);
				dialog.setText("请选择需要打开的文件");
				dialog.setFilterExtensions(new String[]{"*.xml"});
				dialog.setFilterNames(new String[] {"xml文件(*.xml)"});
				filePath = new String[1];
				filePath[0]=dialog.open();
				if (filePath[0]!=null){
					filename=dialog.getFileName();
					openFile();
				}
			}
		});
		
		mntmEdit = new MenuItem(menu, SWT.CASCADE);
		mntmEdit.setText("Edit");
		
		menu_1 = new Menu(mntmEdit);
		mntmEdit.setMenu(menu_1);
		
		
		mntmSegWord = new MenuItem(menu_1, SWT.NONE);
		mntmSegWord.setText("Seperate and rid stopwords");
		mntmSegWord.setAccelerator(SWT.F3);
		
		
		mntmLdaNew = new MenuItem(menu_1, SWT.NONE);
		mntmLdaNew.setText("Do LDA");
		mntmLdaNew.setAccelerator(SWT.F4);
		
		mntmGetAnswer = new MenuItem(menu_1, SWT.NONE);
		mntmGetAnswer.setText("Get best answer");
		mntmGetAnswer.setAccelerator(SWT.F5);
		mntmGetAnswer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(ifSegged == true){
					GetAnswerPageSet();
					removeGetAnswer();
//					GetAnswer();
				}
				else{
					MessageBox messagebox=new MessageBox(shell,SWT.YES|SWT.ICON_ERROR);
					messagebox.setText("Error");
					messagebox.setMessage("请先打开XML文件并进行分词后再进行最佳答案预测");
					messagebox.open();
				}
			}
		});
		
		mntmGetAccuracy = new MenuItem(menu_1, SWT.NONE);
		mntmGetAccuracy.setText("Get the Precision");
		mntmGetAccuracy.setAccelerator(SWT.F6);
		mntmGetAccuracy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(ifSegged == true){
					GetAccuracyPageSet();
//					GetAnswer();
				}
				else{
					MessageBox messagebox=new MessageBox(shell,SWT.YES|SWT.ICON_ERROR);
					messagebox.setText("Error");
					messagebox.setMessage("请先打开XML文件并进行分词后再进行推荐答案准确率计算");
					messagebox.open();
				}
			}
		});
		
		
		mntmLdaNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (ifSegged == true){
					LdaChoose();
				}
				else{
					MessageBox messagebox=new MessageBox(shell,SWT.YES|SWT.ICON_ERROR);
					messagebox.setText("Error");
					messagebox.setMessage("请先打开XML文件并进行分词后再进行LDA");
					messagebox.open();					
				}
			}
		});
		mntmSegWord.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(ifSegged == false){
					segWords = new SegWords();
					segWords.segWords(xmlReader.content_list, xmlReader.ans1_answer_list, 
							xmlReader.ans2_answer_list, xmlReader.ans3_answer_list, 
							xmlReader.ans4_answer_list, xmlReader.ans5_answer_list);
				}
				compositeSegWord.setVisible(true);
				sc3.setVisible(true);
				compositeSegWord.layout(true);
				
				showSegMessage();		
				ifSegged = true;
			}
		});

		
		
//		WHEN OPEN THE XML FILE
		sc1 = new ScrolledComposite(shell, SWT.BORDER | SWT.V_SCROLL);
		sc1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		sc1.setBounds(100, 147, 386, 560);
		sc1.setVisible(false);
		
		compositeTitle = new Composite(sc1,SWT.NONE);
		compositeTitle.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		compositeTitle.setSize(386, 520);
		compositeTitle.setLayout(new RowLayout(SWT.HORIZONTAL));
		compositeTitle.setVisible(false);
		sc1.setContent(compositeTitle);
		compositeTitle.addMouseTrackListener(new MouseTrackAdapter(){
			public void mouseEnter(MouseEvent e){
				sc1.setFocus();
			}
		});
		
		sc2 = new ScrolledComposite(shell, SWT.BORDER | SWT.V_SCROLL);
		sc2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		sc2.setBounds(528, 147, 386, 250);
		sc2.setVisible(false);
		
		compositeContent = new Composite(sc2,SWT.NONE);
		compositeContent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		compositeContent.setSize(386, 250);
		compositeContent.setLayout(new RowLayout(SWT.HORIZONTAL));
		compositeContent.setVisible(false);
		sc2.setContent(compositeContent);
		/*compositeContent.addMouseTrackListener(new MouseTrackAdapter(){
			public void mouseEnter(MouseEvent e){
				if (textContent.getText()!="")
					textContent.setFocus();
			}
		});*/
		
		labelTitle = new Label(shell, SWT.NONE);
		labelTitle.setFont(SWTResourceManager.getFont("Lucida Grande", 50, SWT.BOLD));
		labelTitle.setAlignment(SWT.CENTER);
		labelTitle.setBounds(99, 50, 824, 60);
		labelTitle.setVisible(false);
		
		sc3 = new ScrolledComposite(shell, SWT.BORDER | SWT.V_SCROLL);
		sc3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		sc3.setBounds(528, 455, 386, 250);
		sc3.setVisible(false);
		
		compositeSegWord = new Composite(sc3,SWT.NONE);
		compositeSegWord.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		compositeSegWord.setSize(386, 250);
		compositeSegWord.setLayout(new RowLayout(SWT.HORIZONTAL));
		compositeSegWord.setVisible(false);
		sc3.setContent(compositeSegWord);
		
		sc4 = new ScrolledComposite(shell, SWT.BORDER | SWT.V_SCROLL);
		sc4.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		sc4.setBounds(600, 175, 300, 400);
		sc4.setVisible(false);
		
		compositeLdaDisplay = new Composite(sc4,SWT.NONE);
		compositeLdaDisplay.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		compositeLdaDisplay.setSize(300, 360);
		compositeLdaDisplay.setLayout(new RowLayout(SWT.HORIZONTAL));
		compositeLdaDisplay.setVisible(false);
		sc4.setContent(compositeLdaDisplay);
		
		sc5 = new ScrolledComposite(shell, SWT.BORDER | SWT.V_SCROLL);
		sc5.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		sc5.setBounds(100, 200, 386, 460);
		sc5.setVisible(false);
		
		compositeQuestionIn = new Composite(sc5,SWT.NONE);
		compositeQuestionIn.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		compositeQuestionIn.setSize(386, 460);
		compositeQuestionIn.setLayout(new RowLayout(SWT.HORIZONTAL));
		compositeQuestionIn.setVisible(false);
		sc5.setContent(compositeQuestionIn);
		
		sc6 = new ScrolledComposite(shell, SWT.BORDER | SWT.V_SCROLL);
		sc6.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		sc6.setBounds(528, 200, 386, 460);
		sc6.setVisible(false);
		
		compositeAnswerOut= new Composite(sc6,SWT.NONE);
		compositeAnswerOut.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		compositeAnswerOut.setSize(386, 460);
		compositeAnswerOut.setLayout(new RowLayout(SWT.HORIZONTAL));
		compositeAnswerOut.setVisible(false);
		sc6.setContent(compositeAnswerOut);
		
				
		labelGetAnswerTitle= new Label(shell, SWT.NONE);
		labelGetAnswerTitle.setFont(SWTResourceManager.getFont("Lucida Grande", 50, SWT.BOLD));
		labelGetAnswerTitle.setAlignment(SWT.CENTER);
		labelGetAnswerTitle.setBounds(99, 50, 824, 60);
		labelGetAnswerTitle.setText("利用LDA模型获取最佳答案");
		labelGetAnswerTitle.setVisible(false);
		
		labelQuestionIn = new Label(shell, SWT.NONE);
		labelQuestionIn.setFont(SWTResourceManager.getFont("Lucida Grande", 15, SWT.BOLD));
		labelQuestionIn.setAlignment(SWT.CENTER);
		labelQuestionIn.setBounds(150, 175, 300, 50);
		labelQuestionIn.setText("请在下方输入问题进行最佳答案获取：");
		labelQuestionIn.setVisible(false);
				
		labelAnswerOut = new Label(shell, SWT.NONE);
		labelAnswerOut.setFont(SWTResourceManager.getFont("Lucida Grande", 15, SWT.BOLD));
		labelAnswerOut.setAlignment(SWT.CENTER);
		labelAnswerOut.setBounds(550, 175, 300, 50);
		labelAnswerOut.setText("获得的最佳答案：");
		labelAnswerOut.setVisible(false);
		
		
		labelLdaTitle = new Label(shell, SWT.NONE);
		labelLdaTitle.setFont(SWTResourceManager.getFont("Lucida Grande", 50, SWT.BOLD));
		labelLdaTitle.setAlignment(SWT.CENTER);
		labelLdaTitle.setBounds(99, 50, 824, 60);
		labelLdaTitle.setText("LDA模型的相关信息");
		labelLdaTitle.setVisible(false);
		
		tableTreeLdaResult = new TableTree(shell, SWT.BORDER | SWT.FULL_SELECTION);
		tableTreeLdaResult.setBounds(50, 175, 500, 400);
		tableLdaResult = tableTreeLdaResult.getTable();		
		tableLdaResult.setHeaderVisible(true);
		tableLdaResult.setLinesVisible(true);		
		String[] tableTreeLdaResultTitle = {"Topic", "Words", "Probability"};
		for (int i = 0; i < tableTreeLdaResultTitle.length ; i ++){
			new TableColumn(tableLdaResult, SWT.LEFT).setText(tableTreeLdaResultTitle[i]);
		}				
		tableTreeLdaResult.setVisible(false);

		
		
		
//		tableTreeLdaResult.setVisible(false);
		
		
		labelLdaResult = new Label(shell, SWT.NONE);
		labelLdaResult.setFont(SWTResourceManager.getFont("Lucida Grande", 50, SWT.BOLD));
		labelLdaResult.setAlignment(SWT.CENTER);
		labelLdaResult.setBounds(99, 50, 824, 60);
		labelLdaResult.setText("LDA结果");
		labelLdaResult.setVisible(false);
		
		labelModelPicture = new Label(shell, SWT.NONE);
		labelModelPicture.setBounds(100, 175, 460, 504);
		labelModelPicture.setImage(new Image(Display.getDefault(), "model picture.png"));
		labelModelPicture.setVisible(false);
		
		
		comboLDAContent = new Combo(shell, SWT.READ_ONLY);
		comboLDAContent.setBounds(650, 250, 250, 22);
		String LdaContents[] = new String[3];
//		LdaContents[0] = "只对提问部分进行lda主题分析";
//		LdaContents[1] = "只对回答部分进行lda主题分析";
//		LdaContents[2] = "问答结合进行lda主题分析";
		LdaContents[0] = "Only Questions";
		LdaContents[1] = "Only Answers";
		LdaContents[2] = "Questions and Answers";
		comboLDAContent.setItems(LdaContents);
		comboLDAContent.setVisible(false);
		
		labelLdaContent = new Label(shell, SWT.NONE);
		labelLdaContent.setFont(SWTResourceManager.getFont("Lucida Grande", 15, SWT.BOLD));
		labelLdaContent.setAlignment(SWT.CENTER);
		labelLdaContent.setBounds(640, 225, 300, 50);
		labelLdaContent.setText("请在下方选择进行lda主题分析的内容：");
		labelLdaContent.setVisible(false);
				
		comboTopicNum = new Combo(shell, SWT.READ_ONLY);
		comboTopicNum.setBounds(650, 325, 100, 22);
		String topicNum[] = new String[5];
		for(int dummyi = 0; dummyi < topicNum.length; dummyi++){
			topicNum[dummyi] = 5 * dummyi + 5 + "";
		}
		comboTopicNum.setItems(topicNum);
		comboTopicNum.setVisible(false);
		
		labelTopicNum = new Label(shell, SWT.NONE);
		labelTopicNum.setFont(SWTResourceManager.getFont("Lucida Grande", 15, SWT.BOLD));
		labelTopicNum.setAlignment(SWT.CENTER);
		labelTopicNum.setBounds(650, 300, 150, 50);
		labelTopicNum.setText("请在下方选择主题数：");
		labelTopicNum.setVisible(false);
		
		ldaChooseButton = new Button(shell, SWT.NONE);
		ldaChooseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ldaChooseButtonPageSet();
				ldaAction();										
			}			
		});
		ldaChooseButton.setBounds(650, 400, 100, 33);
		ldaChooseButton.setText("确定");
		ldaChooseButton.setVisible(false);
		
		
		getAnswerButton = new Button(shell, SWT.NONE);
		getAnswerButton.setBounds(200, 680, 100, 33);
		getAnswerButton.setText("获取最佳答案");
		getAnswerButton.setVisible(false);
		getAnswerButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e){
				if (textQuestionIn.getText().length() != 0)
				{
					getAnswer();
				}
				else{
					MessageBox messagebox=new MessageBox(shell,SWT.YES|SWT.ICON_ERROR);
					messagebox.setText("Error");
					messagebox.setMessage("请先输入问题才能进行最佳答案匹配");
					messagebox.open();
				}
			}
		});
				
		returnMainButton2 = new Button(shell, SWT.NONE);
		returnMainButton2.setBounds(650, 680, 100, 33);
		returnMainButton2.setText("返回主界面");
		returnMainButton2.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e){
				sc1.setVisible(true);
				sc2.setVisible(true);
				sc3.setVisible(true);
				labelTitle.setVisible(true);
//				segWordButton.setVisible(true);
				
				sc5.setVisible(false);
				sc6.setVisible(false);
				compositeQuestionIn.setVisible(false);
				compositeAnswerOut.setVisible(false);
				labelGetAnswerTitle.setVisible(false);
				labelQuestionIn.setVisible(false);
				labelAnswerOut.setVisible(false);
				getAnswerButton.setVisible(false);
				returnMainButton2.setVisible(false);
			}
		});
		returnMainButton2.setVisible(false);
		
		returnLdaChooseButton = new Button(shell, SWT.NONE);
		returnLdaChooseButton.setBounds(700, 650 , 100, 33);
		returnLdaChooseButton.setText("返回LDA选择");
		returnLdaChooseButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e){
				removeLdaContent();
				returnLdaChooseButtonPageSet();								
			}
		});
		returnLdaChooseButton.setVisible(false);
		
		
		returnMainButton1 = new Button(shell, SWT.NONE);
		returnMainButton1.setBounds(650, 450, 100, 33);
		returnMainButton1.setText("返回主界面");
		returnMainButton1.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e){
				sc1.setVisible(true);
				sc2.setVisible(true);
				sc3.setVisible(true);
				labelTitle.setVisible(true);
//				segWordButton.setVisible(true);
				
				ldaChooseButton.setVisible(false);
				labelLdaTitle.setVisible(false);
				labelLdaContent.setVisible(false);
				labelTopicNum.setVisible(false);
				comboLDAContent.setVisible(false);
				comboTopicNum.setVisible(false);
				returnMainButton1.setVisible(false);
				labelModelPicture.setVisible(false);
			}
		});
		returnMainButton1.setVisible(false);
		

	} 
	


	/**
	 * open xml file
	 */
	private void openFile(){
		removeAll();
		xmlReader = new XMLReader();
		xmlReader.readXml(filePath[0]);
		labelTitle.setText(filename.substring(0, filename.lastIndexOf(".")));
		sc1.setVisible(true);
		sc2.setVisible(true);
		labelTitle.setVisible(true);
		titleSize=xmlReader.title_list.size();
		pageAll=(titleSize-1)/NUMBER_PER_PAGE+1;
		page=1;
		printTwenty(page);
		showDetailMessage();
	}
	
	/**
	 * Show the details of the xml doc	
	 */
	private void showDetailMessage() {
		// TODO Auto-generated method stub
		questionNum = 0;
		answerNum = 0;
		questionNum = xmlReader.id_list.size();
		ArrayList<String> allAnswerNum = new ArrayList<String>();
		allAnswerNum.addAll(xmlReader.ans1_answer_list);
		allAnswerNum.addAll(xmlReader.ans2_answer_list);
		allAnswerNum.addAll(xmlReader.ans3_answer_list);
		allAnswerNum.addAll(xmlReader.ans4_answer_list);
		allAnswerNum.addAll(xmlReader.ans5_answer_list);
		for (int i = 0; i < allAnswerNum.size();i++){
			if (allAnswerNum.get(i).length() != 0){
				answerNum += 1;
			}
		}
		MessageBox messagebox=new MessageBox(shell,SWT.YES|SWT.ICON_ERROR);
		messagebox.setText("Details:");
		messagebox.setMessage("已成功读入XML文档\n\n共有" + questionNum + "份文档和问题\n共有" + answerNum + "份回答\n");
		messagebox.open();		
	}

	/**
	 * 显示分词成功并说明分好的词共有多少个，多少中
	 */
	private void showSegMessage(){
		if (ifSegged ==false){
			msgSeg = getSegWords();
		}
		MessageBox messagebox=new MessageBox(shell,SWT.YES|SWT.ICON_ERROR);
		messagebox.setText("Attention");
		messagebox.setMessage(msgSeg);
		messagebox.open();
	}
	
	private String getSegWords() {
		// TODO Auto-generated method stub
		int wordType = 0;
		TreeSet<String> h = new TreeSet<String>();
		characterNum = 0;
		collectAllSegAnswer();
		String[] temp;
		
		
		ArrayList<String> equalSet = new ArrayList<String>();
		ArrayList<String> list = new ArrayList<String>();

		
		for (int i= 0 ; i < segAnswerAll.size(); i ++){
			temp = segAnswerAll.get(i).split("\\s+");
			characterNum += temp.length;
			for(int k = 0; k < temp.length; k ++){
				list.add(temp[k]);
				if(h.add(temp[k])==true){
					equalSet.add(temp[k]);
				}			    								
			}			
		}
		
		int[] damnRepeat = new int[equalSet.size()];
		for(int i = 0; i<equalSet.size();i++){
			damnRepeat[i] = 0;
		}
		
		for(int i = 0; i < list.size(); i++){
			damnRepeat[equalSet.indexOf(list.get(i))] += 1;			
		}

		int repeatMore1 = 0;
		int repeatMore100 = 0;
		int repeatMore200 = 0;
		int repeatMore500 = 0;
		
		int repeatMore10 = 0;
		int repeatMore50 = 0;
		
		int max=0;
		int maxIndex = -1;
		for(int i = 0;i<equalSet.size();i++){
			if (damnRepeat[i] > 1){
				repeatMore1 += 1;
			}
			if (damnRepeat[i] >= 200){
				repeatMore200 += 1;
			}
			if (damnRepeat[i] >= 100){
				repeatMore100 += 1;
			}
			if (damnRepeat[i] >= 500){
				repeatMore500 += 1;
			}
			
			if (damnRepeat[i] >= 10){
				repeatMore10 += 1;
			}
			
			if (damnRepeat[i] >= 50){
				repeatMore50 += 1;
			}
			
			if(damnRepeat[i] > max){
				 max = damnRepeat[i];
				 maxIndex = i;
			}
		}
		System.out.println("最多的重复词的重复数量为：" + max + "   是：" + equalSet.get(maxIndex) + "重复" + damnRepeat[maxIndex]);
		
		System.out.println("重复至少2词共有" + repeatMore1);		
		
		System.out.println("重复至少10次共有" + repeatMore10);
		System.out.println("重复至少50次共有" + repeatMore50);
		
		System.out.println("重复至少100次共有" + repeatMore100);
		System.out.println("重复至少200次共有" + repeatMore200);
		System.out.println("重复至少500次共有" + repeatMore500);
		
		wordType = h.size();
		
		String msgResult = "分词成功，并已去除了停词\n\n总共分词分了 " + characterNum + 
				" 个词语\n其中共有" + wordType + " 个不同词语\n其中重复至少2词的共有" + repeatMore1 +
				" 个词语\n其中重复至少10次共有" + repeatMore10 + " 个词语\n其中重复至少50次共有" + 
				repeatMore50 + " 个词语\n其中重复至少100词的共有" + repeatMore100 + 
				" 个词语\n其中重复至少200词的共有" + repeatMore200 + " 个词语\n其中重复至少500词的共有" + 
				repeatMore500 + " 个词语";
		
		return msgResult;
	}

	/**
	 * 进入lda参数选择界面
	 */
	private void LdaChoose(){
		sc1.setVisible(false);
		sc2.setVisible(false);
		sc3.setVisible(false);
		labelTitle.setVisible(false);
		
//		segWordButton.setVisible(false);
		
		ldaChooseButton.setVisible(true);
		labelLdaTitle.setVisible(true);
		labelLdaContent.setVisible(true);
		labelTopicNum.setVisible(true);
		comboLDAContent.setVisible(true);
		comboTopicNum.setVisible(true);
		returnMainButton1.setVisible(true);
		labelModelPicture.setVisible(true);
	}
	
	
	/**
	 * lda参数选择界面的界面设置
	 */
	private void ldaChooseButtonPageSet(){
		sc4.setVisible(true);
		compositeLdaDisplay.setVisible(true);
		compositeLdaDisplay.layout(true);
		returnLdaChooseButton.setVisible(true);
		labelLdaResult.setVisible(true);
		tableTreeLdaResult.setVisible(true);
		
		ldaChooseButton.setVisible(false);
		labelLdaTitle.setVisible(false);
		labelLdaContent.setVisible(false);
		labelTopicNum.setVisible(false);
		comboLDAContent.setVisible(false);
		comboTopicNum.setVisible(false);
		returnMainButton1.setVisible(false);
		labelModelPicture.setVisible(false);
	}
	
	/**
	 * 内容显示设置<br>
	 * 从lda结果显示界面转到lda参数选择界面的内容显示设置
	 */
	private void returnLdaChooseButtonPageSet(){
		sc4.setVisible(false);
		compositeLdaDisplay.setVisible(false);
		returnLdaChooseButton.setVisible(false);
		labelLdaResult.setVisible(false);
		tableTreeLdaResult.setVisible(false);
		
		
		ldaChooseButton.setVisible(true);
		labelLdaTitle.setVisible(true);
		labelLdaContent.setVisible(true);
		labelTopicNum.setVisible(true);
		comboLDAContent.setVisible(true);
		comboTopicNum.setVisible(true);
		returnMainButton1.setVisible(true);
		labelModelPicture.setVisible(true);		
	}
	
	/**
	 * 显示设置，显示正确率的界面
	 */
	private void GetAccuracyPageSet(){
		if (ifGetAccuracy == false){
			if (ifSampled == false){
				doSampling();
				collectAllSegAnswer();
			}
			getPrecision();
		}
		MessageBox messagebox=new MessageBox(shell,SWT.YES|SWT.ICON_ERROR);
		messagebox.setText("result");
		messagebox.setMessage("计算正确率完成！"+"\n\n" + "共有" + totalDoc + "份文档" + "\n"+
				"其中有超过1条回复的有" + docMoreAns2 + "\n推荐答案正确的有" + correctAnswer + 
				"份\n" + "正确率为" + precision);
		messagebox.open();
	}
	
	
	/**
	 * 内容显示设置<br>
	 * 显示输入问题和最佳答案匹配界面的相关内容显示设置
	 */
	private void GetAnswerPageSet() {
		// TODO Auto-generated method stub
		sc5.setVisible(true);
		sc6.setVisible(true);
		compositeQuestionIn.setVisible(true);
		compositeAnswerOut.setVisible(true);
		labelGetAnswerTitle.setVisible(true);
		labelQuestionIn.setVisible(true);
		labelAnswerOut.setVisible(true);
		getAnswerButton.setVisible(true);
		returnMainButton2.setVisible(true);
		
		sc4.setVisible(false);
		compositeLdaDisplay.setVisible(false);
		returnLdaChooseButton.setVisible(false);
		labelLdaResult.setVisible(false);
		tableTreeLdaResult.setVisible(false);
		ldaChooseButton.setVisible(false);
		labelLdaTitle.setVisible(false);
		labelLdaContent.setVisible(false);
		labelTopicNum.setVisible(false);
		comboLDAContent.setVisible(false);
		comboTopicNum.setVisible(false);
		returnMainButton1.setVisible(false);
		labelModelPicture.setVisible(false);
		sc1.setVisible(false);
		sc2.setVisible(false);
		sc3.setVisible(false);
		labelTitle.setVisible(false);
	}
	
	/**
	 * 显示XML每条文档的标题，20个一组
	 * @param page1
	 */
	private void printTwenty(int page1){
		int start,end,j;
		
		start=(page1-1)*NUMBER_PER_PAGE;
		end=page1*NUMBER_PER_PAGE-1;
		j=0;
		chosen=NUMBER_PER_PAGE;
		
		RowData rd2=new RowData();
		rd2.height=30;
		lastPageUp = new Text(compositeTitle, SWT.READ_ONLY);
		lastPageUp.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
		lastPageUp.setFont(SWTResourceManager.getFont("宋体", 11, SWT.BOLD));
		lastPageUp.setLayoutData(rd2);
		lastPageUp.setText("\u4E0A\u4E00\u9875");
		lastPageUp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lastPageUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				page--;
				remove();
				printTwenty(page);
			}
		});
		
		nextPageUp = new Text(compositeTitle, SWT.READ_ONLY);
		nextPageUp.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
		nextPageUp.setFont(SWTResourceManager.getFont("宋体", 11, SWT.BOLD));
		nextPageUp.setLayoutData(rd2);
		nextPageUp.setText("\u4E0B\u4E00\u9875");
		nextPageUp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		nextPageUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				page++;
				remove();
				printTwenty(page);
			}
		});
		
		textUp1 = new Text(compositeTitle, SWT.READ_ONLY);
		textUp1.setFont(SWTResourceManager.getFont("宋体", 11, SWT.BOLD));
		textUp1.setLayoutData(rd2);
		textUp1.setText("当前第"+page+"页，共"+pageAll+"页,转至第");
		textUp1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		goUp = new Text(compositeTitle, SWT.BORDER | SWT.CENTER);
		goUp.setText(""+page);
		goUp.setLayoutData(new RowData(25,10));
		goUp.addVerifyListener(new VerifyListener(){     
			@Override
			public void verifyText(VerifyEvent e) {
				// TODO Auto-generated method stub   
				boolean b = ("0123456789".indexOf(e.text)>=0);     
				e.doit = b;
			}     
		});
		goUp.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.keyCode==SWT.CR){
					if (goUp.getText()==""){
						MessageBox messagebox = new MessageBox(shell, SWT.YES
								|SWT.ICON_ERROR);  
		                messagebox.setText("Error");  
		                messagebox.setMessage("请输入要转至的页数");
		                messagebox.open();
					}
					else {
						int pagethis=Integer.parseInt(goUp.getText());
						if (pagethis>pageAll || pagethis<1){
							MessageBox messagebox = new MessageBox(shell, SWT.YES
									|SWT.ICON_ERROR);  
			                messagebox.setText("Error");  
			                messagebox.setMessage("该文件只有"+pageAll+"页，请输入一个1到"+pageAll+
			                		"之间的整数");
			                messagebox.open();
						}
						else{
							page=pagethis;
							remove();
							printTwenty(page);
						}
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		textUp2 = new Text(compositeTitle, SWT.READ_ONLY);
		textUp2.setFont(SWTResourceManager.getFont("宋体", 11, SWT.BOLD));
		textUp2.setLayoutData(rd2);
		textUp2.setText("页");
		textUp2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		btnGoUp=new Button(compositeTitle,SWT.NONE);
		btnGoUp.setFont(SWTResourceManager.getFont("宋体", 11, SWT.NORMAL));
		btnGoUp.setLayoutData(new RowData(65,20));
		btnGoUp.setText("确定");
		btnGoUp.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
		btnGoUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (goUp.getText()==""){
					MessageBox messagebox = new MessageBox(shell, SWT.YES|
							SWT.ICON_ERROR);  
	                messagebox.setText("Error");  
	                messagebox.setMessage("请输入要转至的页数");
	                messagebox.open();
				}
				else {
					int pagethis=Integer.parseInt(goUp.getText());
					if (pagethis>pageAll){
						MessageBox messagebox = new MessageBox(shell, SWT.YES|
								SWT.ICON_ERROR);  
		                messagebox.setText("Error");  
		                messagebox.setMessage("该文件只有"+pageAll+"页，请输入一个1到"+pageAll+
		                		"之间的整数");
		                messagebox.open();
					}
					else{
						page=pagethis;
						remove();
						printTwenty(page);
					}
				}
			}
		});
		
		for (int i=start;i<=end && i<titleSize;i++){
			final int index1=i;
			//patient pat=(patient)patientList.patientsTitle.get(i);
			textTitle[j]=new Text(compositeTitle, SWT.READ_ONLY | SWT.MULTI | SWT.WRAP );
			textTitle[j].setText(xmlReader.title_list.get(i)+"\n");
			RowData rd=new RowData();
			rd.width=300;
			textTitle[j].setLayoutData(rd);
			textTitle[j].setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
			textTitle[j].setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			final Text texta=textTitle[j];
			textTitle[j].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseUp(MouseEvent e) {
					removePaintListeners();
					if (chosen!=NUMBER_PER_PAGE){
						CustomPaintListener listener=new CustomPaintListener();
						listener.setColor(SWTResourceManager.getColor(SWT.COLOR_WHITE));
						listener.setLocation(textTitle[chosen].getLocation().x-1,
								textTitle[chosen].getLocation().y-1);
						listener.setSize(textTitle[chosen].getSize().x+1, 
								textTitle[chosen].getSize().y+1);
						compositeTitle.addPaintListener(listener);
						compositeTitle.redraw();
					}
					int index=0;
					while(texta.getLocation().y!=textTitle[index].getLocation().y)
						index++;
					chosen=index;
					CustomPaintListener listener=new CustomPaintListener();
					listener.setColor(SWTResourceManager.getColor(SWT.COLOR_RED));
					listener.setLocation(texta.getLocation().x-1,texta.getLocation().y-1);
					listener.setSize(texta.getSize().x+1, texta.getSize().y+1);
					compositeTitle.addPaintListener(listener);
					compositeTitle.redraw();
					removeContent();
					printContent(index1);
					if (ifSegged == true){
						printSegged(index1);
					}
				}
			});
			texta.addMouseTrackListener(new MouseTrackAdapter() {
				@Override
				public void mouseEnter(MouseEvent e) {
					int index=0;
					while(texta.getLocation().y!=textTitle[index].getLocation().y)
						index++;
					if (index!=chosen){
						listener1=new CustomPaintListener();
						listener1.setColor(SWTResourceManager.getColor(SWT.COLOR_BLUE));
						listener1.setLocation(texta.getLocation().x-1,texta.getLocation().y-1);
						listener1.setSize(texta.getSize().x+1, texta.getSize().y+1);
						compositeTitle.addPaintListener(listener1);
						compositeTitle.redraw();
					}
					sc1.setFocus();
				}
				public void mouseExit(MouseEvent e){
					int index=0;
					while(texta.getLocation().y!=textTitle[index].getLocation().y)
						index++;
					if (index!=chosen){
						compositeTitle.removePaintListener(listener1);
						CustomPaintListener listener=new CustomPaintListener();
						listener.setColor(SWTResourceManager.getColor(SWT.COLOR_WHITE));
						listener.setLocation(texta.getLocation().x-1,texta.getLocation().y-1);
						listener.setSize(texta.getSize().x+1, texta.getSize().y+1);
						compositeTitle.addPaintListener(listener);
						compositeTitle.redraw();
					}
				}
				
			});
			j++;
		}
		
		space = new Text(compositeTitle,SWT.READ_ONLY);
		space.setLayoutData(new RowData(386,15));
		space.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		lastPageDown = new Text(compositeTitle, SWT.READ_ONLY);
		lastPageDown.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
		lastPageDown.setFont(SWTResourceManager.getFont("宋体", 11, SWT.BOLD));
		lastPageDown.setLayoutData(rd2);
		lastPageDown.setText("\u4E0A\u4E00\u9875");
		lastPageDown.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lastPageDown.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				page--;
				remove();
				printTwenty(page);
			}
		});
		
		nextPageDown = new Text(compositeTitle, SWT.READ_ONLY);
		nextPageDown.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
		nextPageDown.setFont(SWTResourceManager.getFont("宋体", 11, SWT.BOLD));
		nextPageDown.setLayoutData(rd2);
		nextPageDown.setText("\u4E0B\u4E00\u9875");
		nextPageDown.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		nextPageDown.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				page++;
				remove();
				printTwenty(page);
			}
		});
		
		textDown1 = new Text(compositeTitle,SWT.READ_ONLY);
		textDown1.setFont(SWTResourceManager.getFont("宋体", 11, SWT.BOLD));
		textDown1.setLayoutData(rd2);
		textDown1.setText("当前第"+page+"页，共"+pageAll+"页,转至第");
		textDown1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		goDown= new Text(compositeTitle, SWT.BORDER | SWT.CENTER);
		goDown.setText(""+page);
		goDown.setLayoutData(new RowData(25,10));
		goDown.addVerifyListener(new VerifyListener(){     
			@Override
			public void verifyText(VerifyEvent e) {
				// TODO Auto-generated method stub
				boolean b = ("0123456789".indexOf(e.text)>=0);     
				e.doit = b;
			}     
		});
		goDown.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.keyCode==SWT.CR){
					if (goDown.getText()==""){
						MessageBox messagebox = new MessageBox(shell, SWT.YES|
								SWT.ICON_ERROR);  
		                messagebox.setText("Error");  
		                messagebox.setMessage("请输入要转至的页数");
		                messagebox.open();
					}
					else {
						int pagethis=Integer.parseInt(goDown.getText());
						if (pagethis>pageAll){
							MessageBox messagebox = new MessageBox(shell, SWT.YES|
									SWT.ICON_ERROR);  
			                messagebox.setText("Error");  
			                messagebox.setMessage("该文件只有"+pageAll+"页，请输入一个1到"+pageAll+
			                		"之间的整数");
			                messagebox.open();
						}
						else{
							page=pagethis;
							remove();
							printTwenty(page);
						}
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		textDown2 = new Text(compositeTitle, SWT.READ_ONLY);
		textDown2.setFont(SWTResourceManager.getFont("宋体", 11, SWT.BOLD));
		rd2.height=30;
		textDown2.setLayoutData(rd2);
		textDown2.setText("页");
		textDown2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		btngoDown=new Button(compositeTitle,SWT.NONE);
		btngoDown.setLayoutData(new RowData(65,20));
		btngoDown.setText("确定");
		btngoDown.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
		btngoDown.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (goDown.getText()==""){
					MessageBox messagebox = new MessageBox(shell, SWT.YES|
							SWT.ICON_ERROR);  
		            messagebox.setText("Error");  
		            messagebox.setMessage("请输入要转至的页数");
		            messagebox.open();
				}
				else {
					int pagethis=Integer.parseInt(goDown.getText());
					if (pagethis>pageAll){
						MessageBox messagebox = new MessageBox(shell, SWT.YES|
								SWT.ICON_ERROR);  
			            messagebox.setText("Error");  
			            messagebox.setMessage("该文件只有"+pageAll+"页，请输入一个1到"+pageAll+
			            		"之间的整数");
			            messagebox.open();
					}
					else{
						page=pagethis;
						remove();
						printTwenty(page);
					}
				}
			}
		});
		
		int height;
		compositeTitle.layout(true);
		height=lastPageDown.getLocation().y+lastPageDown.getSize().y;
		if (height>300){
			compositeTitle.setSize(386, height);
		}
		if (page==1) {
			lastPageUp.setEnabled(false);
			lastPageDown.setEnabled(false);
		}
		else {
			lastPageUp.setEnabled(true);
			lastPageDown.setEnabled(true);
		}
		if (page==pageAll){
			nextPageUp.setEnabled(false);
			nextPageUp.setEnabled(false);
		}
		else {
			nextPageUp.setEnabled(true);
			nextPageDown.setEnabled(true);
		}
		
		
		compositeTitle.setVisible(true);
		sc1.setVisible(true);
		compositeTitle.layout(true);
		compositeContent.setVisible(true);
		sc2.setVisible(true);
		goUp.setFocus();
		compositeContent.layout(true);
		
//		segWordButton.setVisible(true);
		
	}

	/**
	 * 清除所有内容
	 */
	private void removeAll(){
		remove();
		ifSegged = false;
		ifSampled = false;
		ifGetAccuracy = false;	
	}
	
	/**
	 * 清除sc1和sc2内容
	 */
	private void remove(){
		Control[] controls=compositeTitle.getChildren();
		for (int i=0;i<controls.length;i++)
			controls[i].dispose();
		compositeTitle.setSize(386,520);
		compositeTitle.setVisible(false);
		removePaintListeners();
		sc1.setVisible(false);
		
		removeContent();
		removeLdaContent();
		removeGetAnswer();
		
		compositeContent.setSize(386,210);
		compositeContent.setVisible(false);
		sc2.setVisible(false);					
	}
	
	/**
	 * 清除主界面的内容（每条XML文档和分词处理后的内容）
	 */
	private void removeContent(){
		Control[] controls=compositeContent.getChildren();
		Control[] controls1 = compositeSegWord.getChildren();
		for (int i=0;i<controls.length;i++)
			controls[i].dispose();
		for (int j = 0;j<controls1.length;j++)
			controls1[j].dispose();
		textContent=new Text(compositeContent,SWT.READ_ONLY|SWT.WRAP|SWT.MULTI);
		textContentSegged=new Text(compositeSegWord,SWT.READ_ONLY|SWT.WRAP|SWT.MULTI);
		textContent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		textContentSegged.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		compositeContent.setSize(386,0);
		compositeContent.layout(true);
		compositeContent.setSize(386,210);
		compositeSegWord.setSize(386,0);
		compositeSegWord.layout(true);
		compositeSegWord.setSize(386,210);
		
	}
	
	
	/**
	 * 清除lda结果显示的界面内容
	 */
	private void removeLdaContent(){
		
		Control[] controls=compositeLdaDisplay.getChildren();
		
		for (int i=0;i<controls.length;i++)
			controls[i].dispose();

		textContentLda=new Text(compositeLdaDisplay,SWT.READ_ONLY|SWT.WRAP|SWT.MULTI);
		
		textContentLda.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		compositeLdaDisplay.setSize(600,0);
		compositeLdaDisplay.layout(true);
		compositeLdaDisplay.setSize(600,400);
		
		tableLdaResult.removeAll();					
	}
	
	/**
	 * 清除问题是输入和最佳答案获得界面的内容
	 */
	private void removeGetAnswer(){
		Control[] controls1 = compositeQuestionIn.getChildren();
		Control[] controls2 = compositeAnswerOut.getChildren();
		
		for (int i=0;i<controls1.length;i++)
			controls1[i].dispose();
		
		for (int i=0;i<controls2.length;i++)
			controls2[i].dispose();
				
		
		compositeQuestionIn.setSize(386,0);
		compositeQuestionIn.layout(true);
		compositeQuestionIn.setSize(386,460);
		
		compositeAnswerOut.setSize(386,0);
		compositeAnswerOut.layout(true);
		compositeAnswerOut.setSize(386,460);
		
		textQuestionIn = new Text(compositeQuestionIn, SWT.BORDER | SWT.WRAP |SWT.MULTI );
		textAnswerOut = new Text(compositeAnswerOut,SWT.READ_ONLY|SWT.WRAP|SWT.MULTI);
		
		textQuestionIn.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		textAnswerOut.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		textQuestionIn.setSize(386, 460);
				
	}
	
	/**
	 * 清除选中的红色框
	 */
	private void removePaintListeners(){
		Listener[] listeners=compositeTitle.getListeners(SWT.Paint);
		for (int i=0;i<listeners.length;i++)
			compositeTitle.removeListener(SWT.Paint,listeners[i]);
	}
	
	/**
	 * 在tableTree中显示lda的结果
	 * @param ldaResultWord
	 * @param ldaResultValue
	 */
	private void printTableLdaResult(ArrayList<String> ldaResultWord,
			ArrayList<Double> ldaResultValue) {
		// TODO Auto-generated method stub
				
		for (int i = 0; i < topicNumber; i++){
			parentLdaResult = new TableTreeItem(tableTreeLdaResult, SWT.BORDER);
			parentLdaResult.setText(0, "topic " + i);
			parentLdaResult.setText(1, "top possible words");
			parentLdaResult.setText(2, "Words' Probability");
			
			for (int j = 0 ; j < topicNumber; j++){
				childLdaResult = new TableTreeItem(parentLdaResult, SWT.BORDER);
				childLdaResult.setText(0, "   Top " + (j+1) + " word");
				childLdaResult.setText(1, "       " + ldaResultWord.get(i * topicNumber + j));
				childLdaResult.setText(2, ldaResultValue.get(i * topicNumber + j)+ "");
			}
			parentLdaResult.setExpanded(false);
		}
		
		TableColumn[] columns = tableLdaResult.getColumns();
		int n = columns.length;
	    for (int k = 0; k < n; k++) {
	    	columns[k].pack();	
	    	columns[k].setWidth(LdaResultColunmsWidth[k]);	    	      
	    }
		
	}
	
	
	/**
	 * 选好lda参数之后，进行lda
	 */
	private void ldaAction(){
		if (topicNumber == Integer.parseInt(comboTopicNum.getText()) && 
				ldaChooseType == comboLDAContent.indexOf(comboLDAContent.getText()))
		{
			printLdaResult(ldaResult);
			printTableLdaResult(ldaResultWord, ldaResultValue);
		}
		
		else{
		
			topicNumber =Integer.parseInt(comboTopicNum.getText());   // topic分为5，10，15，20，25共5档

			ldaChooseType = comboLDAContent.indexOf(comboLDAContent.getText());				
			// 0代表“提问”分析， 1代表“回答”分析， 2代表“问答”分析（0，1结合）				
			if (ldaChooseType == 0){
				corpus = Corpus.loading(segWords.non_stopwords_seg_content_list);
			}
			else if (ldaChooseType == 1){
				collectAllSegAnswer();
				corpus = Corpus.loading(segAnswerAll);
			}
			else if(ldaChooseType == 2){
				corpus = Corpus.loadQuestionAnswer(segWords.non_stopwords_seg_content_list,segAnswerAll);
			}
			ldaGibbsSampler = new LdaGibbsSampler(corpus.getDocument(), corpus.getVocabularySize());
	        ldaGibbsSampler.gibbs(topicNumber);
	        double[][] phi = ldaGibbsSampler.getPhi();
	        topicMap = LdaUtil.translate(phi, corpus.getVocabulary(), topicNumber);
	        ldaResult = new ArrayList<String>();
	        ldaResultWord = new ArrayList<String>();
	        ldaResultValue = new ArrayList<Double>();
	        LdaUtil.explain2(topicMap, ldaResultWord, ldaResultValue);
	        LdaUtil.explain(topicMap, ldaResult);		        

//				removeLdaContent();
			printLdaResult(ldaResult);
			printTableLdaResult(ldaResultWord, ldaResultValue);
		}
	}
	
	/**
	 * 进行Gibbs Sampling <br>
	 * 获得所有答案的 phi 和 theta
	 */
	private void doSampling(){
		collectAllSegAnswer();
		corpus = Corpus.loading(segAnswerAll);
		ldaGibbsSampler = new LdaGibbsSampler(corpus.getDocument(), corpus.getVocabularySize());
        ldaGibbsSampler.gibbs(BestTopicNum);
        phiAnswer = ldaGibbsSampler.getPhi();
		thetaAnswer = ldaGibbsSampler.getTheta();
	}
	
	
	/**
	 * 将所有分好词后的答案集中到一个arraylist中
	 */
	private void collectAllSegAnswer(){
		segAnswerAll = new ArrayList<String> ();
		ArrayList<String> segAnswerAllTemp = new ArrayList<String> ();
		segAnswerAllTemp.addAll(segWords.non_stopwords_seg_ans1_answer_list);
		segAnswerAllTemp.addAll(segWords.non_stopwords_seg_ans2_answer_list);
		segAnswerAllTemp.addAll(segWords.non_stopwords_seg_ans3_answer_list);
		segAnswerAllTemp.addAll(segWords.non_stopwords_seg_ans4_answer_list);
		segAnswerAllTemp.addAll(segWords.non_stopwords_seg_ans5_answer_list);
		segAnswerAllTemp.remove(null);
		System.out.println("seg:"+segAnswerAllTemp.size());		
		int a = 0;
		for (int i = 0; i < segAnswerAllTemp.size();i++){
			if (segAnswerAllTemp.get(i).length()  != 0){
				a += 1;
				segAnswerAll.add(segAnswerAllTemp.get(i));
			}
		}
		System.out.println("seg2:" + a);
	}
	

	/**
	 * 将所有的答案集中到一个arraylist中,并去除空白的answer
	 */
	private void collectAllAnswer(){
		answerAll = new ArrayList<String> ();
		ArrayList<String> answerAllTemp = new ArrayList<String> ();
		answerAllTemp.addAll(xmlReader.ans1_answer_list);
		answerAllTemp.addAll(xmlReader.ans2_answer_list);
		answerAllTemp.addAll(xmlReader.ans3_answer_list);
		answerAllTemp.addAll(xmlReader.ans4_answer_list);
		answerAllTemp.addAll(xmlReader.ans5_answer_list);	
		answerAllTemp.remove(null);
		
		ArrayList<String> segAnswerAllTemp = new ArrayList<String> ();
		segAnswerAllTemp.addAll(segWords.non_stopwords_seg_ans1_answer_list);
		segAnswerAllTemp.addAll(segWords.non_stopwords_seg_ans2_answer_list);
		segAnswerAllTemp.addAll(segWords.non_stopwords_seg_ans3_answer_list);
		segAnswerAllTemp.addAll(segWords.non_stopwords_seg_ans4_answer_list);
		segAnswerAllTemp.addAll(segWords.non_stopwords_seg_ans5_answer_list);
		
		
		System.out.println("all:"+answerAllTemp.size());	
		int a = 0;
		for (int i = 0; i < answerAllTemp.size();i++){
			if (answerAllTemp.get(i).length() != 0 && segAnswerAllTemp.get(i).length() !=0){
				a += 1;
				answerAll.add(answerAllTemp.get(i));
			}
			if (answerAllTemp.get(i).length() != 0 && segAnswerAllTemp.get(i).length() ==0){
				System.out.println("strange:" + answerAllTemp.get(i));
			}
		}
		System.out.println("all2:" + a);
	}	
	
	/**
	 * 计算得到推荐答案的正确率
	 */
	private void getPrecision(){
		double scoreProduct,scoreSum, maxScore;
		int wordID;
		int m1,m = 0,w,k,q;
		int recomAnswerIndex = -1;
//		int recomAnswerIndex2 = -1;
		correctAnswer = 0;
		maxScore = -1.0;
		totalDoc = segWords.non_stopwords_seg_content_list.size();
		docMoreAns2 = 0;
		
//		collectAllAnswer();
		
		for (q = 0 ; q < totalDoc; q ++){
			segQuestionWords2 = Arrays.asList(segWords.non_stopwords_seg_content_list.get(q).split("\\s+"));
			if(segWords.non_stopwords_seg_ans2_answer_list.get(q).length() == 0 && 
					segWords.non_stopwords_seg_ans3_answer_list.get(q).length()==0 && 
					segWords.non_stopwords_seg_ans4_answer_list.get(q).length()==0 && 
					segWords.non_stopwords_seg_ans5_answer_list.get(q).length()==0)
				continue;
			docMoreAns2 += 1;
			for (m1 = 0 ; m1 < 5; m1++){
				
				if(m1 == 1){
					if (segWords.non_stopwords_seg_ans2_answer_list.get(q).length() == 0) continue;
					m = segAnswerAll.indexOf(segWords.non_stopwords_seg_ans2_answer_list.get(q));
				}
									
				else if(m1 == 2){
					if (segWords.non_stopwords_seg_ans3_answer_list.get(q).length() == 0) continue;
					m = segAnswerAll.indexOf(segWords.non_stopwords_seg_ans3_answer_list.get(q));
				}
				
				else if(m1 == 3){
					if (segWords.non_stopwords_seg_ans4_answer_list.get(q).length() == 0) continue;
					m = segAnswerAll.indexOf(segWords.non_stopwords_seg_ans4_answer_list.get(q));
				}
				
				else if(m1 == 4){
					if (segWords.non_stopwords_seg_ans5_answer_list.get(q).length() == 0) continue;
					m = segAnswerAll.indexOf(segWords.non_stopwords_seg_ans5_answer_list.get(q));
				}
				
				else{
					m = segAnswerAll.indexOf(segWords.non_stopwords_seg_ans1_answer_list.get(q));
				}
				
								
				scoreProduct = 0.0;
				
				for (w = 0 ; w < segQuestionWords2.size(); w++){
					if (corpus.getVocabulary().getId(segQuestionWords2.get(w)) == null){
						continue;
					}
					wordID = corpus.getVocabulary().getId(segQuestionWords2.get(w));
					scoreSum = 0.0;
					for (k = 0; k < BestTopicNum; k++){
						scoreSum += phiAnswer[k][wordID] * thetaAnswer[m][k];
					}
					if (scoreProduct == 0.0 || scoreSum == 0.0){
						scoreProduct = scoreSum;
					}
					else{
						scoreProduct *= scoreSum;
					}
				}
				if (maxScore < scoreProduct){
					maxScore = scoreProduct;
					recomAnswerIndex = m;
				}
			}
			
			if (segWords.non_stopwords_seg_ans1_answer_list.contains(segAnswerAll.get(recomAnswerIndex)) == true){
				if (segWords.non_stopwords_seg_ans1_answer_list.indexOf(segAnswerAll.get(recomAnswerIndex)) == q){
					correctAnswer += 1;
				}
			}
			
//			if (xmlReader.ans1_answer_list.contains(answerAll.get(recomAnswerIndex)) == true){
//				if (xmlReader.ans1_answer_list.indexOf(answerAll.get(recomAnswerIndex)) == q){
//					correctAnswer += 1;
//				}
//			}
//			else if (xmlReader.ans2_answer_list.contains(answerAll.get(recomAnswerIndex)) == true){
//				recomAnswerIndex2 = xmlReader.ans2_answer_list.indexOf(answerAll.get(recomAnswerIndex));
//			}
//			else if (xmlReader.ans3_answer_list.contains(answerAll.get(recomAnswerIndex)) == true){
//				recomAnswerIndex2 = xmlReader.ans3_answer_list.indexOf(answerAll.get(recomAnswerIndex));
//			}
//			else if (xmlReader.ans4_answer_list.contains(answerAll.get(recomAnswerIndex)) == true){
//				recomAnswerIndex2 = xmlReader.ans4_answer_list.indexOf(answerAll.get(recomAnswerIndex));
//			}
//			else if (xmlReader.ans5_answer_list.contains(answerAll.get(recomAnswerIndex)) == true){
//				recomAnswerIndex2 = xmlReader.ans5_answer_list.indexOf(answerAll.get(recomAnswerIndex));
//			}
//			if (recomAnswerIndex2 == q){
//				correctAnswer += 1;
//			}
			
			maxScore = -1.0;
			
		}		
		precision = 1.0 * correctAnswer / (1.0 * docMoreAns2);
		ifGetAccuracy = true;
		ifSampled = true;
	}
	
	/**
	 * 获取最佳答案
	 */
	private void getAnswer() {
		// TODO Auto-generated method stub
		questionContent = textQuestionIn.getText();
		segQuestionWords1 = segWords.segQuestionWords(questionContent);
		
		if (ifSampled == false){			
			doSampling();
			collectAllSegAnswer();
		}				

		double scoreProduct,scoreSum, maxScore;
		int wordID;
		int m,w,k;
		maxScore = -1.0;
		for (m = 0; m < segAnswerAll.size();m++){
			scoreProduct = 0.0;
			for (w = 0; w < segQuestionWords1.size(); w++){
				if (corpus.getVocabulary().getId(segQuestionWords1.get(w)) == null){
					continue;
				}
				wordID = corpus.getVocabulary().getId(segQuestionWords1.get(w));
				scoreSum = 0.0;
				for (k = 0; k < BestTopicNum; k++){
					scoreSum += phiAnswer[k][wordID] * thetaAnswer[m][k];
				}
				if (scoreProduct == 0.0 || scoreSum == 0.0){
					scoreProduct = scoreSum;
				}
				else{
					scoreProduct *= scoreSum;
				}
			}
			if (maxScore < scoreProduct){
				maxScore = scoreProduct;
				BestAnswerIndex = m;
			}
		}
		printBestAnswer();
		ifSampled = true;
	}
	
	/**
	 * 在文本框中显示最佳答案
	 */
	private void printBestAnswer() {
		// TODO Auto-generated method stub
		collectAllAnswer();
		textAnswerOut.setText("						推荐最佳答案：		" + "\n"+ "\n");
		textAnswerOut.append("【推荐答案】：" + answerAll.get(BestAnswerIndex) + "\n");
		
		RowData rd=new RowData();
		rd.width=355;
		textAnswerOut.setLayoutData(rd);
		textAnswerOut.addMouseWheelListener(new MouseWheelListener(){

			@Override
			public void mouseScrolled(MouseEvent e) {
				// TODO Auto-generated method stub
				Point p=sc6.getOrigin();
				ScrollBar bar=sc6.getVerticalBar();
				int i=bar.getSelection();
				i=i-e.count*5;
				sc6.setOrigin(p.x, p.y-e.count*5);
				bar.setSelection(i);
				sc6.layout(true);
			}
			
		});
				
		
		
		compositeAnswerOut.layout(true);
		int height=textAnswerOut.getLocation().y+textAnswerOut.getSize().y;
		if (height>300){
			compositeAnswerOut.setSize(386,height);
		}
		textAnswerOut.setFocus();
		compositeAnswerOut.layout(true);
		
	}
	

	/**
	 * 在文本框中显示lda结果
	 * @param ldaResult2
	 */
	private void printLdaResult(ArrayList<String> ldaResult2) {
		// TODO Auto-generated method stub
		textContentLda=new Text(compositeLdaDisplay,SWT.READ_ONLY|SWT.WRAP|SWT.MULTI);		
		
		textContentLda.setFont(SWTResourceManager.getFont("Lucida Grande", 16, SWT.BOLD));
		
		textContentLda.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		textContentLda.setText("		lda结果显示：");
		for (int j = 0; j < ldaResult2.size(); j++){
			textContentLda.append("\n\n" + ldaResult2.get(j));
			textContentLda.append("\n" + "-----------------------------------------------" + "\n");
		}
		RowData rd=new RowData();
		rd.width=550;
		textContentLda.setLayoutData(rd);
		textContentLda.addMouseWheelListener(new MouseWheelListener(){

			@Override
			public void mouseScrolled(MouseEvent e) {
				// TODO Auto-generated method stub
				Point p=sc4.getOrigin();
				ScrollBar bar=sc4.getVerticalBar();
				int i=bar.getSelection();
				i=i-e.count*5;
				sc4.setOrigin(p.x, p.y-e.count*5);
				bar.setSelection(i);
				sc4.layout(true);
			}
		});
		
		compositeLdaDisplay.layout(true);
		int height=textContentLda.getLocation().y+textContentLda.getSize().y;
		if (height>300){
			compositeLdaDisplay.setSize(575 ,height);
		}
		textContentLda.setFocus();
		compositeLdaDisplay.layout(true);
	}

	/**
	 * 在文本框中显示分词结果
	 * @param index
	 */
	private void printSegged(int index){
		textContentSegged.setText("						分词并去除停词后：		" + "\n"+ "\n");
		textContentSegged.append("【问题分析】：" + segWords.non_stopwords_seg_content_list.get(index) + "\n");
		textContentSegged.append("【回复人1分析】：" + segWords.non_stopwords_seg_ans1_answer_list.get(index) + "\n");
		textContentSegged.append("【回复人2分析】：" + segWords.non_stopwords_seg_ans2_answer_list.get(index) + "\n");
		textContentSegged.append("【回复人3分析】：" + segWords.non_stopwords_seg_ans3_answer_list.get(index) + "\n");
		textContentSegged.append("【回复人4分析】：" + segWords.non_stopwords_seg_ans4_answer_list.get(index) + "\n");
		textContentSegged.append("【回复人5分析】：" + segWords.non_stopwords_seg_ans5_answer_list.get(index) + "\n");
		
		RowData rd=new RowData();
		rd.width=355;
		textContentSegged.setLayoutData(rd);
		textContentSegged.addMouseWheelListener(new MouseWheelListener(){

			@Override
			public void mouseScrolled(MouseEvent e) {
				// TODO Auto-generated method stub
				Point p=sc3.getOrigin();
				ScrollBar bar=sc3.getVerticalBar();
				int i=bar.getSelection();
				i=i-e.count*5;
				sc3.setOrigin(p.x, p.y-e.count*5);
				bar.setSelection(i);
				sc3.layout(true);
			}
			
		});
				
		
		
		compositeSegWord.layout(true);
		int height=textContentSegged.getLocation().y+textContentSegged.getSize().y;
		if (height>250){
			compositeSegWord.setSize(386,height);
		}
//		textContentSegged.setFocus();
		compositeSegWord.layout(true);
		
	}
	
	
	/**
	 * 在文本框中显示每条XML文档的内容
	 * @param index
	 */
	private void printContent(int index){
				
		textContent.setText("【问题】 " + "\n");
		textContent.append("	问题标题："+xmlReader.title_list.get(index) + "\n");
		textContent.append("	问题内容：" + xmlReader.content_list.get(index) + "\n");
		textContent.append("【提问者信息】" + "\n");
		textContent.append("	提问者用户名：" + xmlReader.user_name_list.get(index) + "\n");
		textContent.append("	提问者性别：" + xmlReader.user_sex_list.get(index) + "\n");
		textContent.append("	提问者年龄：" + xmlReader.user_age_list.get(index) + "\n");
		textContent.append("	提问时间：" + xmlReader.ask_time_list.get(index) + "\n");
		
		textContent.append("【回复人1】" + "\n");
		textContent.append("	姓名：" + xmlReader.ans1_name_list.get(index)+ "\n");
		textContent.append("	职称：" + xmlReader.ans1_job_title_list.get(index) + "\n");
		textContent.append("	分析：" + xmlReader.ans1_answer_list.get(index) + "\n");
		textContent.append("	回复时间：" + xmlReader.ans1_time_list.get(index) + "\n");
		
		textContent.append("【回复人2】" + "\n");
		textContent.append("	姓名：" + xmlReader.ans2_name_list.get(index)+ "\n");
		textContent.append("	职称：" + xmlReader.ans2_job_title_list.get(index) + "\n");
		textContent.append("	分析：" + xmlReader.ans2_answer_list.get(index) + "\n");
		textContent.append("	回复时间：" + xmlReader.ans2_time_list.get(index) + "\n");
		
		textContent.append("【回复人3】" + "\n");
		textContent.append("	姓名：" + xmlReader.ans3_name_list.get(index)+ "\n");	
		textContent.append("	职称：" + xmlReader.ans3_job_title_list.get(index) + "\n");
		textContent.append("	分析：" + xmlReader.ans3_answer_list.get(index) + "\n");
		textContent.append("	回复时间：" + xmlReader.ans3_time_list.get(index) + "\n");
		
		textContent.append("【回复人4】" + "\n");
		textContent.append("	姓名：" + xmlReader.ans4_name_list.get(index)+ "\n");
		textContent.append("	职称：" + xmlReader.ans4_job_title_list.get(index) + "\n");
		textContent.append("	分析：" + xmlReader.ans4_answer_list.get(index) + "\n");
		textContent.append("	回复时间：" + xmlReader.ans4_time_list.get(index) + "\n");
		
		textContent.append("【回复人5】" + "\n");
		textContent.append("	姓名：" + xmlReader.ans5_name_list.get(index)+ "\n");
		textContent.append("	职称：" + xmlReader.ans5_job_title_list.get(index) + "\n");
		textContent.append("	分析：" + xmlReader.ans5_answer_list.get(index) + "\n");
		textContent.append("	回复时间：" + xmlReader.ans5_time_list.get(index) + "\n");
		
		RowData rd=new RowData();
		rd.width=355;
		textContent.setLayoutData(rd);
		textContent.addMouseWheelListener(new MouseWheelListener(){

			@Override
			public void mouseScrolled(MouseEvent e) {
				// TODO Auto-generated method stub
				Point p=sc2.getOrigin();
				ScrollBar bar=sc2.getVerticalBar();
				int i=bar.getSelection();
				i=i-e.count*5;
				sc2.setOrigin(p.x, p.y-e.count*5);
				bar.setSelection(i);
				sc2.layout(true);
			}
			
		});
				
		
		
		compositeContent.layout(true);
		int height=textContent.getLocation().y+textContent.getSize().y;
		if (height>250){
			compositeContent.setSize(386,height);
		}
//		textContent.setFocus();
		compositeContent.layout(true);
		
	}
}
