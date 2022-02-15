# data-sync

监控MySQL中数据表如若有数据新增，则向指定的数据库进行数据更新

## 项目缺陷

这个项目目前我工作中有用到，只是稍微简化我的工作，实际意义不大，毕竟局限性太高
- 只能监控MySQL，因为我是监控
`information_schema.TABLES`，比如PostgreSQL就没有更新时间就无法监控。
  
- 必须有自增字段，不然增量更新的时候无法获取增量数据

## 开发逻辑

- 每次查询`information_schema.TABLES`该表，如果`CREATE_TIME`、`UPDATE_TIME`大于上次扫描时间，则放入到队列中
- 有一个线程池监控队列，队列中有任务则执行任务
