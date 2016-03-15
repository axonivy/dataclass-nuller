package ch.ivyteam.ivy.dataclass.helper;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.ivyteam.ivy.scripting.objects.Date;
import ch.ivyteam.ivy.scripting.objects.List;
import ch.ivyteam.ivy.scripting.objects.util.IvyDefaultValues;

public class AutoInitNullerTest 
{
	private TstClassFlat flat;
	private AutoInitNuller nuller;

	@Before
	public void setUp() throws Exception 
	{
		flat = new TstClassFlat();
		flat.setMyInt(IvyDefaultValues.DEFAULT_INTEGER);
		flat.setMyNumber(IvyDefaultValues.DEFAULT_NUMBER);
		flat.setMyString(IvyDefaultValues.DEFAULT_STRING);
		
		nuller = AutoInitNuller.getNuller();
	}

	@After
	public void tearDown() throws Exception 
	{
		flat = null;
		nuller = null;
	}

	@Test
	public void testFlat() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException 
	{
		assertEquals(3, nuller.nullDeep(flat));
	}
	
	@Test
	public void testFlatWithManualNull() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException 
	{
		flat.setMyDate(null);
		assertEquals(3, nuller.nullDeep(flat));
	}
	
	@Test
	public void testFlatWithManualObject() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException 
	{
		flat.setMyDate(new Date());
		assertEquals(3, nuller.nullDeep(flat));
	}
	
	@Test
	public void testDeep() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException 
	{
		TstClassDeep deep = new TstClassDeep();
		deep.setMyTstClass(flat);
		deep.setMyDate(IvyDefaultValues.DEFAULT_DATE);
		assertEquals(4, nuller.nullDeep(deep));
	}
	
	@Test
	public void testDeepCycle() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException 
	{
		TstClassDeep deep = new TstClassDeep();
		TstClassDeep deepDeep = new TstClassDeep();
		deepDeep.setMyInt(IvyDefaultValues.DEFAULT_INTEGER);
		deepDeep.setMyTstClass(deep);
		deep.setMyString(IvyDefaultValues.DEFAULT_STRING);
		deep.setMyTstClass(deepDeep);
		assertEquals(2, nuller.nullDeep(deep));
	}

	@Test
	public void testListSimple() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException 
	{
		TstClassCollections coll = new TstClassCollections();
		List<Integer> ivyList = List.create(Integer.class);
		ivyList.add(0);
		ivyList.add(10);
		ivyList.add(IvyDefaultValues.DEFAULT_INTEGER);
		ivyList.add(null);
		coll.setMyIvyList(ivyList);
		java.util.List<String> javaList = new ArrayList<String>();
		javaList.add("");
		javaList.add(null);
		javaList.add(IvyDefaultValues.DEFAULT_STRING);
		javaList.add("hallo");
		coll.setMyJavaList(javaList);
		assertEquals(2, nuller.nullDeep(coll));
	}
//	
//	@Test
//	public void testListDeep() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException 
//	{
//		TstClassCollections coll = new TstClassCollections();
//		java.util.List<List<Integer>> javaList = new ArrayList<List<Integer>>();
//		List<Integer> ivyDeepList = List.create(Integer.class);
//		ivyDeepList.add(0);
//		ivyDeepList.add(10);
//		ivyDeepList.add(IvyDefaultValues.DEFAULT_INTEGER);
//		ivyDeepList.add(null);	
//		javaList.add(ivyDeepList);
//		javaList.add(null);
//		javaList.add(List.create(Integer.class));
//		coll.setMyJavaList(javaList);
//
//		assertEquals(1, nuller.nullDeep(coll));
//	}
//	
//	@Test
//	public void testPropertyUtils() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
//	{
//		java.util.List<String> myList = new ArrayList<String>();
//		myList.add("hello");
//		myList.add("Michael");
//		myList.add("you");
//		myList.add("are");
//		myList.add("cool");
//		
//		TstClassCollections tst = new TstClassCollections();
//		tst.setMyJavaList(myList);
//		
//		PropertyUtils.setIndexedProperty(tst, "myJavaList", 1, "Carlos");
//		assertEquals("Carlos", myList.get(1));
//		assertEquals("Carlos", tst.getMyJavaList().get(1));
//	}
}
