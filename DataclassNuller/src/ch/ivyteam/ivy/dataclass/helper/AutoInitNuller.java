package ch.ivyteam.ivy.dataclass.helper;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;

import ch.ivyteam.ivy.scripting.objects.util.IvyDefaultValues;

public class AutoInitNuller 
{
	// avoid external creation of instances
	private AutoInitNuller() {}
	
	/**
	 * Getter for stateless instance. 
	 * @return a thread safe instance of the nuller
	 */
	public static AutoInitNuller getNuller()
	{
		return new AutoInitNuller();
	}
	
	public int nullDeep(Object bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		int nulledMembers = 0;
		LinkedList<Object> beansToVisit = new LinkedList<Object>();
		Set<Object> visitedBeans = new HashSet<Object>();
		
		beansToVisit.add(bean);
		while (!beansToVisit.isEmpty())
		{
			nulledMembers += nullNext(beansToVisit, visitedBeans);
		}
		return nulledMembers;
	}
	

	private int nullNext(LinkedList<Object> beansToVisit, Set<Object> visitedBeans) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		int nulledMembers = 0;
		Object bean = beansToVisit.remove(0);
		for (PropertyDescriptor desc : PropertyUtils.getPropertyDescriptors(bean.getClass()))
		{
			if (shouldVisitMember(desc))
			{
				String memberName = desc.getName();
				Object memberValue = PropertyUtils.getProperty(bean, memberName);
				if (memberValue != null)
				{
					if (isDefaultValue(desc, memberValue))
					{
						PropertyUtils.setProperty(bean, memberName, null);
						nulledMembers++;
					}
					else if (memberValue instanceof Collection<?>)
					{
						nulledMembers += handleCollections(bean, memberName, memberValue, beansToVisit, visitedBeans);
					}
					else if (!visitedBeans.contains(memberValue))
					{
						beansToVisit.addLast(memberValue);
					}
				}
			}
		}
		visitedBeans.add(bean);
		return nulledMembers;
	}

	@SuppressWarnings("unchecked")
	private int handleCollections(Object bean, 
			String memberName, Object memberValue, 
			LinkedList<Object> beansToVisit, Set<Object> visitedBeans) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException 
	{
		int nulledMembers = 0;
		int index = 0;
		Collection memberCollection = (Collection) memberValue;
		for (Object memberMember : memberCollection)
		{
			if (memberMember != null)
			{
				if (IvyDefaultValues.isDefaultObject(memberMember))
				{
					if (memberValue instanceof Set)
					{
						Set memberSet = (Set) memberCollection;
						memberSet.remove(memberMember);
						nulledMembers++;
					}
					if (memberValue instanceof List)
					{
						PropertyUtils.setIndexedProperty(bean, memberName, index, null);
						nulledMembers++;
					}
					if (memberValue instanceof Map)
					{
						PropertyUtils.setMappedProperty(bean, memberName, null);
						nulledMembers++;
					}
				}
				else if (!visitedBeans.contains(memberMember) && memberMember != null)
				{
					beansToVisit.addLast(memberMember);
				}
			}
			index++;
		}
		return nulledMembers;
	}

	private boolean isDefaultValue(PropertyDescriptor desc, Object memberValue) 
	{
		return IvyDefaultValues.isDefaultObject(memberValue) && (desc.getWriteMethod() != null);
	}

	private boolean shouldVisitMember(PropertyDescriptor desc) 
	{
		return !desc.getName().equals("class") && desc.getReadMethod() != null;
	}
	
}
