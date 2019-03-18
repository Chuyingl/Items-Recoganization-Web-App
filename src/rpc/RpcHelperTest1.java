package rpc;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;

import entity.Item;
import entity.Item.ItemBuilder;


class RpcHelperTest1 {

	public void testGetJSONArray() throws JSONException {
		Set<String> category = new HashSet<String>();
		category.add("category one");
	    ItemBuilder builder1=new ItemBuilder();
	    builder1.setItemId("one");
	    builder1.setAddress("1781 Cram Cir");
		Item one =builder1.build();
		ItemBuilder builder2 =new ItemBuilder();
		builder1.setItemId("two");
		builder1.setAddress("1781 Cram Cir");
		Item two =builder2.build();
		List<Item> listItem = new ArrayList<Item>();
		listItem.add(one);
		listItem.add(two);
		JSONArray jsonArray = new JSONArray();
		jsonArray.put(one.toJSONObject());
		jsonArray.put(two.toJSONObject());
		JSONAssert.assertEquals(jsonArray, RpcHelper.getJSONArray(listItem), true);
	}
	

}
