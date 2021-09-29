package me.test.demarcationqueue.lib;

public interface LuaScriptConst {
    String QUEUE_READ_AND_ADD_ACK = "" +
            "local queueKey = KEYS[1] \n" +
            "local ackKey = KEYS[2] \n" +
            "local fetchSize = ARGV[1] \n" +
            "local ackPrefix = ARGV[2] \n" +
            "local time = redis.call('TIME')[1] \n" +
            "local list = redis.call('lrange',queueKey,0,fetchSize-1) \n" +
            "if not rawequal(next(list), nil) then  \n" +
            "	local size = 0 \n" +
            "	local map = {} \n" +
            "	for index, item in pairs(list) do \n" +
            "		size = index \n" +
            "    	table.insert(map,ackPrefix .. time) \n" +
            "    	table.insert(map,item) \n" +
            "	end \n" +
            "	redis.call('ZADD',ackKey, unpack(map)) \n" +
            "	redis.call('ltrim',queueKey,size,-1) \n" +
            "end \n" +
            "return list;";
}
