//package assignment;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import java.time.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;

class Main {
    
	public static void insertionSort(int[] arr)
	{
		for(int j = 1; j < arr.length; j++)
		{
			int key = arr[j];
			int i = j-1;
			while(i>=0 && arr[i]>key)
			{
				arr[i+1] = arr[i];
				i--;
			}
			arr[i+1] = key;
		}
	}
	
	public static void merge(int[] a, int[] aux, int lo, int mid, int hi) //Merge Sort
	{
		for(int k = lo; k<=hi; k++) {aux[k] = a[k];}
		
		int i = lo, j = mid+1;
		
		for(int k = lo; k<=hi; k++)
		{
			if(i > mid) {a[k] = aux[j++];}
			else if(j > hi) {a[k] = aux[i++];}
			else if(aux[j] < aux[i]) {a[k] = aux[j++];}
			else {a[k] = aux[i++];}
		}
	}
	
	public static void recMergeSort(int[] a, int[] aux, int lo, int hi)
	{
		if(hi <= lo) {return;}
		int mid = lo + (hi - lo)/2;
		recMergeSort(a, aux, lo, mid);
		recMergeSort(a, aux, mid+1, hi);
		merge(a, aux, lo, mid, hi);
	}
	
	public static void mergeSort(int[] a)
	{
		int[] aux = new int[a.length];
		recMergeSort(a, aux, 0, a.length - 1);
	}
	
	public static void pigeonholeSort(int[] a)
	{
		int n = a.length;
		int min = a[0];
		int max = a[0];
		for(int i = 0; i < n; i++)
		{
			if(a[i] > max) {max = a[i];}
			if(a[i] < min) {min = a[i];}
		}
		int range = max - min + 1;
		int[] holes = new int[range];
		Arrays.fill(holes, 0);
		  
        for (int i = 0; i < n; i++)
        {
        	holes[a[i] - min]++;
        }
  
        int index = 0;
  
        for (int j = 0; j < range; j++)
        {
            while (holes[j]-- > 0)
            {
                a[index++] = j + min;
            }
        }
	}
	
	public static void countingSort(int[] array, int size)
	{
		int[] output = new int[size + 1];
	    int max = array[0];
	    for (int i = 1; i < size; i++) {
	      if (array[i] > max)
	        max = array[i];
	    }
	    int[] count = new int[max + 1];
	    for (int i = 0; i < max; ++i) {
	      count[i] = 0;
	    }
	    for (int i = 0; i < size; i++) {
	      count[array[i]]++;
	    }
	    for (int i = 1; i <= max; i++) {
	      count[i] += count[i - 1];
	    }
	    for (int i = size - 1; i >= 0; i--) {
	      output[count[array[i]] - 1] = array[i];
	      count[array[i]]--;
	    }
	    for (int i = 0; i < size; i++) {
	      array[i] = output[i];
	    }
    }
	
	public static double takeAverage(ArrayList<Double> list)
	{
		double sum = 0;
		for(double i: list)
		{
			sum+=i;
		}
		double average = sum/list.size();
		return average;
	}
	
	public static void reverse(int a[], int n)
    {
        int[] b = new int[n];
        int j = n;
        for (int i = 0; i < n; i++) {
            b[j - 1] = a[i];
            j = j - 1;
        }
  
        for (int k = 0; k < n; k++) {
            a[k] = b[k];
        }
    }
	
	public static void isSorted(int[] a)
	{
		for(int i = 1; i<a.length; i++)
		{
			if(a[i-1] > a[i])
			{
				System.out.println("Not Sorted!");
				return;
			}
			
		}
		System.out.println("Sorted!");
	}
	
	public static void showNums(ArrayList<ArrayList<Double>> res)
	{
		for(ArrayList<Double> arr: res)
		{
			for(double i: arr)
			{
				System.out.printf("%.1f" + " ", i);
			}
			System.out.println();
		}
	}
	
	
	public static void main(String args[]) throws IOException {
    	
    	//read the CSV file
		ArrayList<String> rawInput = new ArrayList<String>();
    	try(BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("TrafficFlowDataset.csv"),StandardCharsets.UTF_8))){
    	    String line;
    	  	while((line=br.readLine())!=null){
    	    	String[] split=line.split(",");
    	      	rawInput.add(split[7]);
    	    }
    	}
    	rawInput.remove(0); // delete the title "Flow Duration"
    	int[] input = new int[rawInput.size()];
    	for(int i = 0; i<rawInput.size(); i++)
    	{
    		input[i] = Integer.parseInt(rawInput.get(i));
    	}
    	
        // X axis data
        int[] inputAxis = {512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251281};
        
        //For random data
        ArrayList<ArrayList<Double>> randomResults = new ArrayList<ArrayList<Double>>();
        for(int i = 0; i<4; i++)
        {
        	ArrayList<Double> temp = new ArrayList<Double>();
        	randomResults.add(temp);
        }
        
        for(int inputRange : inputAxis)
        {
        	ArrayList<Double> trials = new ArrayList<Double>();
        	for(int j = 0; j<10; j++)
        	{
        		int[] sample = Arrays.copyOfRange(input, 0, inputRange+1);
        		Instant start = Instant.now();
                insertionSort(sample);
                Instant finish = Instant.now();
                double timeElapsed = Duration.between(start, finish).toMillis();
                trials.add(timeElapsed);
        	}
        	double result = takeAverage(trials);
        	randomResults.get(0).add(result);
        }
        
        for(int inputRange : inputAxis)
        {
        	ArrayList<Double> trials = new ArrayList<Double>();
        	for(int j = 0; j<10; j++)
        	{
        		int[] sample = Arrays.copyOfRange(input, 0, inputRange+1);
        		Instant start = Instant.now();
                mergeSort(sample);
                Instant finish = Instant.now();
                double timeElapsed = Duration.between(start, finish).toMillis();
                trials.add(timeElapsed);
        	}
        	double result = takeAverage(trials);
        	randomResults.get(1).add(result);
        }
        
        
        for(int inputRange : inputAxis)
        {
        	ArrayList<Double> trials = new ArrayList<Double>();
        	for(int j = 0; j<10; j++)
        	{
        		int[] sample = Arrays.copyOfRange(input, 0, inputRange+1);
        		Instant start = Instant.now();
                pigeonholeSort(sample);
                Instant finish = Instant.now();
                double timeElapsed = Duration.between(start, finish).toMillis();
                trials.add(timeElapsed);
        	}
        	double result = takeAverage(trials);
        	randomResults.get(2).add(result);
        }
        
        
        for(int inputRange : inputAxis)
        {
        	ArrayList<Double> trials = new ArrayList<Double>();
        	for(int j = 0; j<10; j++)
        	{
        		int[] sample = Arrays.copyOfRange(input, 0, inputRange+1);
        		Instant start = Instant.now();
                countingSort(sample, sample.length);
                Instant finish = Instant.now();
                double timeElapsed = Duration.between(start, finish).toMillis();
                trials.add(timeElapsed);
        	}
        	double result = takeAverage(trials);
        	randomResults.get(3).add(result);
        }
        
        
        //For Sorted Data
        Arrays.sort(input);
        ArrayList<ArrayList<Double>> sortedResults = new ArrayList<ArrayList<Double>>();
        for(int i = 0; i<4; i++)
        {
        	ArrayList<Double> temp = new ArrayList<Double>();
        	sortedResults.add(temp);
        }
        for(int inputRange : inputAxis)
        {
        	ArrayList<Double> trials = new ArrayList<Double>();
        	int[] sample = Arrays.copyOfRange(input, 0, inputRange+1);
        	for(int j = 0; j<10; j++)
        	{
        		Instant start = Instant.now();
                insertionSort(sample);
                Instant finish = Instant.now();
                double timeElapsed = Duration.between(start, finish).toMillis();
                trials.add(timeElapsed);
        	}
        	double result = takeAverage(trials);
        	sortedResults.get(0).add(result);
        }
        
        for(int inputRange : inputAxis)
        {
        	ArrayList<Double> trials2 = new ArrayList<Double>();
        	int[] sample2 = Arrays.copyOfRange(input, 0, inputRange+1);
        	for(int j = 0; j<10; j++)
        	{
        		Instant start = Instant.now();
                mergeSort(sample2);
                Instant finish = Instant.now();
                double timeElapsed = Duration.between(start, finish).toMillis();
                trials2.add(timeElapsed);
        	}
        	double result = takeAverage(trials2);
        	sortedResults.get(1).add(result);
        }
        
        for(int inputRange : inputAxis)
        {
        	ArrayList<Double> trials3 = new ArrayList<Double>();
        	int[] sample3 = Arrays.copyOfRange(input, 0, inputRange+1);
        	for(int j = 0; j<10; j++)
        	{
        		Instant start = Instant.now();
                pigeonholeSort(sample3);
                Instant finish = Instant.now();
                double timeElapsed = Duration.between(start, finish).toMillis();
                trials3.add(timeElapsed);
        	}
        	double result = takeAverage(trials3);
        	sortedResults.get(2).add(result);
        }
        
        for(int inputRange : inputAxis)
        {
        	ArrayList<Double> trials4 = new ArrayList<Double>();
        	int[] sample4 = Arrays.copyOfRange(input, 0, inputRange+1);
        	
        	for(int j = 0; j<10; j++)
        	{
        		Instant start = Instant.now();
                countingSort(sample4, sample4.length);
                Instant finish = Instant.now();
                double timeElapsed = Duration.between(start, finish).toMillis();
                trials4.add(timeElapsed);
        	}
        	double result = takeAverage(trials4);
        	sortedResults.get(3).add(result);
        }
        
        
        //For reverse sorted data
        mergeSort(input);
        reverse(input, input.length);
        ArrayList<ArrayList<Double>> reverseResults = new ArrayList<ArrayList<Double>>();
        for(int i = 0; i<4; i++)
        {
        	ArrayList<Double> temp = new ArrayList<Double>();
        	reverseResults.add(temp);
        }
        for(int inputRange : inputAxis)
        {
        	ArrayList<Double> trials = new ArrayList<Double>();
        	for(int j = 0; j<10; j++)
        	{
        		int[] sample = Arrays.copyOfRange(input, 0, inputRange+1);
        		Instant start = Instant.now();
                insertionSort(sample);
                Instant finish = Instant.now();
                double timeElapsed = Duration.between(start, finish).toMillis();
                trials.add(timeElapsed);
        	}
        	double result = takeAverage(trials);
        	reverseResults.get(0).add(result);
        }
        for(int inputRange : inputAxis)
        {
        	ArrayList<Double> trials = new ArrayList<Double>();
        	for(int j = 0; j<10; j++)
        	{
        		int[] sample = Arrays.copyOfRange(input, 0, inputRange+1);
        		Instant start = Instant.now();
                mergeSort(sample);
                Instant finish = Instant.now();
                double timeElapsed = Duration.between(start, finish).toMillis();
                trials.add(timeElapsed);
        	}
        	double result = takeAverage(trials);
        	reverseResults.get(1).add(result);
        }
        for(int inputRange : inputAxis)
        {
        	ArrayList<Double> trials = new ArrayList<Double>();
        	for(int j = 0; j<10; j++)
        	{
        		int[] sample = Arrays.copyOfRange(input, 0, inputRange+1);
        		Instant start = Instant.now();
                pigeonholeSort(sample);
                Instant finish = Instant.now();
                double timeElapsed = Duration.between(start, finish).toMillis();
                trials.add(timeElapsed);
        	}
        	double result = takeAverage(trials);
        	reverseResults.get(2).add(result);
        }
        for(int inputRange : inputAxis)
        {
        	ArrayList<Double> trials = new ArrayList<Double>();
        	for(int j = 0; j<10; j++)
        	{
        		int[] sample = Arrays.copyOfRange(input, 0, inputRange+1);
        		Instant start = Instant.now();
                countingSort(sample, sample.length);
                Instant finish = Instant.now();
                double timeElapsed = Duration.between(start, finish).toMillis();
                trials.add(timeElapsed);
        	}
        	double result = takeAverage(trials);
        	reverseResults.get(3).add(result);
        }
        
        showNums(reverseResults);
		
        //Graph example
        // Create sample data for linear runtime
        double[][] yAxis = new double[4][10];
        for(int i = 0; i<4; i++)
        {
        	for(int j = 0; j<10; j++)
        	{
        		//yAxis[i][j] = randomResults.get(i).get(j);
        		yAxis[i][j] = sortedResults.get(i).get(j);
        		//yAxis[i][j] = reverseResults.get(i).get(j);
        	}
        }
        

        // Save the char as .png and show it
        showAndSaveChart("Sorted Input Test", inputAxis, yAxis);
    }

    public static void showAndSaveChart(String title, int[] xAxis, double[][] yAxis) throws IOException {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries("insertionSort", doubleX, yAxis[0]);
        chart.addSeries("mergeSort", doubleX, yAxis[1]);
        chart.addSeries("pigeonholeSort", doubleX, yAxis[2]);
        chart.addSeries("countingSort", doubleX, yAxis[3]);

        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }
}

