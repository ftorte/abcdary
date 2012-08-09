package com.welmo.educational.scenes.description.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class EventDescriptionsManager {
	public enum Events {
	    NO_EVENTS, ON_CLICK, ON_MOVE
	}
	
	private static EventDescriptionsManager pInstance = null;
	
	//Manage list of Modifier per eachi objec per each event
	private HashMap<Events, HashMap<Object,List<Modifier>>> modifiers;
	private HashMap<Events, HashMap<Object,List<Action>>> actions;
	
	private EventDescriptionsManager(){
		modifiers = new HashMap<Events, HashMap<Object,List<Modifier>>>();
		actions = new HashMap<Events, HashMap<Object,List<Action>>>();
		
	}
	
	public List<Action> getActionList(Events evt, Object o){
		HashMap<Object,List<Action>> pObjectEvent = actions.get(evt);
		if(pObjectEvent == null)
			return null;
		return pObjectEvent.get(o);
	}
	public List<Modifier> getModifierList(Events evt, Object o){
		HashMap<Object,List<Modifier>> pObjectEvent = modifiers.get(evt);
		if(pObjectEvent == null)
			return null;
		return pObjectEvent.get(o);
	}
	
	public void addAction(Events evt, Object obj, Action act){
		//check if event key exist 
		HashMap<Object,List<Action>> pObjectEvent = actions.get(evt);
		if(pObjectEvent == null)
			actions.put(evt, (pObjectEvent = new HashMap<Object,List<Action>>()));
		
		//check if object key exist
		List<Action> pActions = pObjectEvent.get(obj);
		if(pActions == null)
			pObjectEvent.put(obj, pActions = new ArrayList<Action>());
	
		pActions.add(act);
	}
	
	public void addModifier(Events evt, Object obj, Modifier act){
		//check if event key exist 
		HashMap<Object,List<Modifier>> pObjectEvent = modifiers.get(evt);
		if(pObjectEvent == null)
			modifiers.put(evt, (pObjectEvent = new HashMap<Object,List<Modifier>>()));
		
		//check if object key exist
		List<Modifier> pActions = pObjectEvent.get(obj);
		if(pActions == null)
			pObjectEvent.put(obj, pActions = new ArrayList<Modifier>());
	
		pActions.add(act);
	}
	public static EventDescriptionsManager getInstance(){
		if(pInstance == null){
			return pInstance =  new EventDescriptionsManager();
		}
		return pInstance;
	}
	
}

