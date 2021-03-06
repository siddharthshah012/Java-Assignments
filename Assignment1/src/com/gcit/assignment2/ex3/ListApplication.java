package com.gcit.assignment2.ex3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class ListApplication {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		// Open the file
		FileInputStream fstream = new FileInputStream("List.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		List<String> listNames = new ArrayList<String>();
		List<String> listMarks = new ArrayList<String>();
		String strLine;
		while ((strLine = br.readLine()) != null)   {
			String[] splitStr = strLine.split("\\s+");
			listNames.add(splitStr[0]);
			listMarks.add(splitStr[1]);
				
		}
		
		br.close();
		
		//listNames.forEach(s -> System.out.println(s));
		//listMarks.forEach(s -> System.out.println(s));
		
		Map<String, Integer> map = new HashMap<String, Integer>();

		for (String temp : listNames) {
			Integer count = map.get(temp);
			map.put(temp, (count == null) ? 1 : count + 1);
		}
		//printMap(map);

		//System.out.println("\nSorted Map");
		Map<String, Integer> treeMap = new TreeMap<String, Integer>(map);
		//printMap(treeMap);
		
		float total;
		int individualMarks;
		int count;
		List<Float> listMarksAvg = new ArrayList<Float>();
		Map<Map<String, Integer>, Float> alphaMap = new HashMap<>();
		List<ArrayList<String>> a = new ArrayList<>();
	    
		for (Map.Entry<String, Integer> entry : treeMap.entrySet()) {
			count =0;
			total =0;
			int j=1;
			for (int i =0; i< listNames.size();i++){
				String test = entry.getKey();
				String test1= listNames.get(i);
				//System.out.println(test.equals(test1));
				if (test.equals(test1)){
					individualMarks = Integer.parseInt(listMarks.get(i));
					total =total + individualMarks;
					count++;
				}
			}
			float avgStudent = total/count;
			listMarksAvg.add(avgStudent);
			
		}
		
		System.out.println("Alphabetical Order list");
		Iterator entries = treeMap.entrySet().iterator();
		Iterator listEntries = listMarksAvg.iterator();
		while (entries.hasNext() && listEntries.hasNext()) {
		    Map.Entry entry = (Map.Entry) entries.next();
		    //Integer key = (Integer)entry.getKey();
		    //Integer value = (Integer)entry.getValue();
		    System.out.print(entry.getKey() + " " + entry.getValue()+" ");
		    System.out.println(listEntries.next());
		    //alphaMap.put(treeMap, (Float)listEntries.next());
		   
		}
		Iterator entries1 = treeMap.entrySet().iterator();
		Iterator listEntries1 = listMarksAvg.iterator();
		while (entries1.hasNext() && listEntries1.hasNext()) {
		    Map.Entry entry = (Map.Entry) entries1.next();
		    String s= (String) entry.getKey();
		    Integer i = (Integer) entry.getValue();
		    
		    alphaMap.put(new HashMap<String, Integer>(){{put(s,i);}}, (Float) listEntries1.next());

		}
		
		Map<Map<String, Integer>,Float> treeMap1 = new HashMap<Map<String, Integer>,Float>(alphaMap);
		
		//Map<Map<String, Integer>,Float>reverseMap = new TreeMap<Map<String, Integer>,Float>;
		//System.out.println(alphaMap);
		/*Iterator entries2 = treeMap1.entrySet().iterator();
		int num=1;
		System.out.println("Merit Based List");
		while (entries1.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			System.out.println(" "+ entry.getKey() + " " + entry.getValue());
			num++;
			
		}*/
		/*
		Map<Map<String, Integer>,Float> finalMeritMap = entriesSortedByValues(treeMap1);
		Iterator finalMerIterator = finalMeritMap.entrySet().iterator();
		int num=1;
		System.out.println("Merit Based List");
		while (finalMerIterator.hasNext()) {
			Map.Entry entry = (Map.Entry) finalMerIterator.next();
			System.out.println(" "+ entry.getKey() + " " + entry.getValue());
			num++;
			
		}*/
		
		System.out.println("Merit list");
		List<Entry<Map<String, Integer>, Float>> finalMeritList = new ArrayList<>();
		finalMeritList = entriesSortedByValues(treeMap1);
		//System.out.println(entriesSortedByValues(treeMap1));
		finalMeritList.forEach(s -> System.out.println(s));
		//System.out.println(total);
		//System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
		
		
		float totalSumAvg = 0;
		float totalAvg =0;
		
		
		double sd=0;
		for (int i =0; i< listMarksAvg.size();i++ ){
			totalSumAvg = listMarksAvg.get(i) +totalSumAvg;
		}
		
		totalAvg = totalSumAvg/listMarksAvg.size();
		 Double toBeTruncated = new Double(totalAvg);
		
		double finalSumAvg=new BigDecimal(toBeTruncated ).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println("Total Number of students: " +listMarksAvg.size());
		System.out.println("Total avg: " + finalSumAvg);
		
		for (int i =0; i< listMarksAvg.size();i++ ){
			
			sd = sd + Math.pow(listMarksAvg.get(i)- finalSumAvg,2);
		}
		double stanDevi = 0;
		
		stanDevi = Math.sqrt(sd/listMarksAvg.size());
				
		double truncateStanDevi = new Double(stanDevi);
		double finalStanDevi=new BigDecimal(truncateStanDevi).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println("Standar Deviation: " + finalStanDevi);
		
		
			
		}
		
	static <K, V extends Comparable<? super V>> List<Entry<K, V>> entriesSortedByValues(
			Map<K, V> map) {

		List<Entry<K, V>> sortedEntries = new ArrayList<Entry<K, V>>(
				map.entrySet());

		Collections.sort(sortedEntries, new Comparator<Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> e1, Entry<K, V> e2) {
				return e2.getValue().compareTo(e1.getValue());
			}
		});

		return sortedEntries;
	}
		
}
