

package com.zicms.web.zjcdn.mapper;

import com.github.abel533.mapper.Mapper;
import com.zicms.web.zjcdn.model.Main;

import java.util.List;

public interface MainMapper extends Mapper<Main>{
	
	void updateIp(String ip,String date,String username);

	List<Main> findIpa(String username);
	
}
