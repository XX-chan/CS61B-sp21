# Gitlet Design Document

**Name**:

## Classes and Data Structures

### Class commit

#### Fields

1. String message
2. Date date
3. String parent
4. String secondparent
5. Map<String,String> blob
6. String uid


### Class Blob
#### Fields

1. String content
2. String uid
3. 

### Class Index
#### Fields

1. Map<String,String> added
    储存已被add的文件名和blobuid
2. Set<String> removed
    储存已移除的文件路径。
3. Set<String> tracked
    储存被追踪的文件。
4. add()
    
5. remove()
    如果： 
   - 要add的文件已在added内的，移除；
   - 收到移除命令的移除
   移除后的文件从tracked移除，且保存现在状态。
   
    

辅助方法：





## Algorithms

## Persistence

