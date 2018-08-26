package com.gosun.birip.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util
{

	public static Pageable parse(String content) throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		Pageable pageable = null;
		JsonNode root = mapper.readTree(content);
		String pk = "page";
		String sk = "size";
		if(!root.has(pk)) {
			pk = "pageSize"; //"\"pageSize\":10,\"pageNumber\":0"
		}
		if(!root.has(sk)) {
			sk = "pageNumber";
		}
		int page = root.get(pk).asInt() -1 ;
		int size = root.get(sk).asInt();
		if(root.has("sort")){
			// {sort : [{direction:"ASC",property:"field"}]}
			String dk = "direction";
			String ak = "property";
			List<Order> orders = new ArrayList<Order>();
			JsonNode sort = root.get("sort");
			for(int i=0; i< sort.size(); i++){				
				JsonNode jn = sort.get(i);
				if(jn.has(dk) && jn.has(ak)) {
					String direction = jn.get(dk).asText();
					String property = jn.get(ak).asText();
					orders.add(new Order(Direction.valueOf(direction),property));
				}				
			}
			if(!orders.isEmpty()) {
				pageable = PageRequest.of(page, size, Sort.by(orders));
			}
		}else{
			pageable = PageRequest.of(page, size);
		}
		return pageable;
	}
}
