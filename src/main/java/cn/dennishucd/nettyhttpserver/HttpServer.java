/*
 * Copyright 2016 Dennis Hu
 *
 * Dennis Hu licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package cn.dennishucd.nettyhttpserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.apache.log4j.Logger;

/**
 * Created by Dennis Hu on June 29th, 2016
 */

public class HttpServer {
	private static Logger logger = Logger.getRootLogger();

	public void start(String host, int port) throws Exception {
		//create a reactor thread pool for NIO acceptor
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		
		//create a reactor thread pool for NIO handler
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap b = new ServerBootstrap();
			
			b.option(ChannelOption.SO_BACKLOG, 1024);
			
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new PushServerInitializer());

			ChannelFuture f = b.bind(host, port).sync();
			
			logger.info("The http server is started on "+host+":"+port);
			
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		String host  = "0.0.0.0";
		Integer port = 8848;
		
		HttpServer server = new HttpServer();
		if (args.length==1){
			host = args[0];
		} else if (args.length==2) {
			host = args[0];
			port = Integer.valueOf(args[1]);
		}
		
		server.start(host, port);
	}
}
