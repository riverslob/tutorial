package me.test.demarcationqueue.lib;

public interface LuaScriptConst {
    String QUEUE_READ_AND_ADD_ACK = "" +
            "local queueKey = KEYS[1] \n" +
            "local ackKey = KEYS[2] \n" +
            "local fetchSize = ARGV[1] \n" +
            "local list = redis.call('lrange',queueKey,0,fetchSize-1) \n" +
            "if not rawequal(next(list), nil) then  \n" +
            "   local currTime = redis.call('TIME')[1] \n" +
            "	local size = 0 \n" +
            "	local map = {} \n" +
            "	for index, item in pairs(list) do \n" +
            "		size = index \n" +
            "    	table.insert(map,currTime) \n" +
            "    	table.insert(map,item) \n" +
            "	end \n" +
            "	redis.call('ZADD',ackKey, unpack(map)) \n" +
            "	redis.call('ltrim',queueKey,size,-1) \n" +
            "end \n" +
            "return list;";
    String QUEUE_PUT_BACK_AND_REMOVE_ACK = "" +
            "local queueKey = KEYS[1] \n" +
            "local ackKey = KEYS[2] \n" +
            "local map = {} \n" +
            "for index, item in pairs(ARGV) do \n" +
            "  	table.insert(map,item) \n" +
            "end \n" +
            "redis.call('lpush',queueKey,unpack(map)) \n" +
            "redis.call('zrem',ackKey,unpack(map))\n" +
            "return 1;";

    String QUEUE_READ_ACK_AND_UPDATE_SCORE = "" +
            "local ackKey = KEYS[1] \n" +
            "local durationSecond = ARGV[1] \n" +
            "local fetchSize = ARGV[2] \n" +
            "local currTime = redis.call('TIME')[1] \n" +
            "local endRange = currTime - durationSecond\n" +
            "local list = redis.call('ZRANGEBYSCORE',ackKey,0,endRange,'LIMIT',0,fetchSize) \n" +
            "if not rawequal(next(list), nil) then  \n" +
            "	local map = {} \n" +
            "	for index, item in pairs(list) do \n" +
            "    	table.insert(map,currTime) \n" +
            "    	table.insert(map,item) \n" +
            "	end \n" +
            "	redis.call('ZADD',ackKey, unpack(map)) \n" +
            "end \n" +
            "return list;";
    String QUEUE_READ_ACK_AND_UPDATE_SCORE_temp = "" +
            "local ackKey = KEYS[1] \nlocal durationSecond = ARGV[1] \nlocal fetchSize = ARGV[2] \nlocal currTime = redis.call('TIME')[1] \nlocal endRange = currTime\nlocal list = redis.call('ZRANGEBYSCORE',ackKey,0,endRange,'LIMIT',0,fetchSize) \nreturn list;";
}
