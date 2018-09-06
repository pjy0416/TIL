##---------------------------------------------- List
#List 변수 생성
list = []

#LIst 변수에 Value 추가
list.append(1)
list.append('d')
print(list)

#List 특정 index 삭제
del list[0]
print(list)

#List 특정 index에 Value 추가
list.insert(0,1)
print(list)

#List 특정 Value 삭제
list.remove('d')
print(list)

#List에는 stack의 pop도 있음
list.pop()

##---------------------------------------------- LinkedList(Node)
#Node Class
class Node:
    def __init__(self,val):
        self.val = val
        self.next = None # the pointer initially points to nothing

#한방향 LinkedList
node1 = Node(12) 
node2 = Node(99) 
node3 = Node(37) 
node1.next = node2 # 12->99
node2.next = node3 # 99->37

temp = node1
print("============= Check a value ===============")
while(temp) :
    print(temp.val)
    temp = temp.next
# the entire linked list now looks like: 12->99->37

##---------------------------------------------- LinkedList(Node)2 (class 이용)
# singly linked list class

class SinglyLinkedList(object):
    def __init__(self):
        headNode = Node("HEAD") # dummy value
        self.head = headNode
        self.tail = headNode
        self.numOfVal = 0
        
    def add(self, val) :
        addNode = Node(val)
        self.tail.next = addNode # 생성시 self.tail에 headnode를 줬으니 head의 haednode에도 값이 대입됨
        self.tail = addNode
        self.numOfVal +=1
    
    def remove(self, val) :
        temp = self.head
        if self.numOfVal == 0 : # Head는 dummy value이기 때문에 없는 것으로 간주
            print("LinkedList is empty")
            return False
        
        elif self.numOfVal == 1 : # 1개 존재시 빠른 로직을 위해 따로 if문을 추가
            self.head.next = None
            self.tail = temp
            self.numOfVal -=1
            return True
        
        else :
            searchNode = temp.next
            while(searchNode is not None) :
                if searchNode.val == val :
                    if searchNode.next is not None :
                        delNode = searchNode
                        searchNode = None
                        temp.next = delNode.next
                    else :
                        temp.next = None
                else :
                    temp = temp.next
                    searchNode = searchNode.next
            self.numOfVal -=1
            return self
        
    def print(self) :
        print("============= Check a value of LinkedList ===============")
        
        if(self.numOfVal ==0) :
            print("LinkedList is empty")
            return False
        
        node = self.head.next
        
        while(node) :
            print(node.val)
            node = node.next
    
    def size(self) :
        return self.numOfVal

########## Test Code
linked_list = SinglyLinkedList()
linked_list.print()
linked_list.add(1)
linked_list.print()
linked_list.remove(1)
linked_list.print()
linked_list.add(2)
linked_list.add(3)
linked_list.add(4)
linked_list.print()
linked_list.remove(2)
linked_list.print()
linked_list.add(1)
linked_list.add(2)
linked_list.add(5)
linked_list.print()
#################