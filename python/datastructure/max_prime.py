## 숫자 추가/제거, 최대값 반환하는 class 생성
class maxMachine :
    def __init__(self) :
        self.list = []
    def addNumber(self,n) :
        self.list.append(n)
    def removeNumber(self,n) :
        self.list.remove(n)
    def getMax(self) :
        return max(self.list)
    
## NxN 행렬을 입력받아서 (합, 최댓값, 두번째 최댓값 구하기)
## (1<= n <=1,000    , 제한시간 1초)
class myMatrix :
    col =0
    hap =0
    def __init__(self) :
        size = int(input())
        if size == 1 :
            self.matrix = []
        else :
            self.matrix = [[0]*size for i in range(size)]
        self.col = size
    def inputNum(self) :
        if self.col >1 :
            for i in range(0,self.col) :
                for j in range(self.col) :
                    inNum = int(input())
                    self.matrix[i][j] = inNum
                    self.hap += inNum        
        else :
            inNum = int(input())
            self.matrix.append(inNum)
            self.hap += inNum
    def printResult(self) :
        if self.col >1 :
            firstMax = max(max(self.matrix))
            temp = self.matrix
            temp = self.removeAll(temp,firstMax)
            nextMax = max(max(temp))
            print("(%d, %d, %d)"%(self.hap, firstMax, nextMax))        
        else :
            firstMax =  max(self.matrix)
            nextMax = 0 # element가 하나기 때문
            print("(%d, %d, %d)"%(self.hap, firstMax, nextMax)) 
    def removeAll(self, temp, firstMax) :
        for i in range(self.col) :
            if firstMax in temp[i] :
                temp[i].remove(firstMax)
                self.removeAll(temp, firstMax)  
        return temp
    
## 소수 판정문제
## 1 <= n <= 1,000,000,000 // 제한시간 1초
## 2 ~ N-1 으로 나누면 시간 복잡도가 매우 나쁨!
## 따라서 sqrt(n)으로 나누어 떨어지면 소수X, 떨어지지 않는다면 소수로 판정!

def isPrime(num) :
    if num <2 :
        return False
    elif num <=3 :
        return True
    else :
        for i in range(2, num+1) :
            if num % math.sqrt(i) == 0 :
                return False
        return True

##### 범위가 주어질 때, 소수가 몇개인지 출력 #####
##### 단, 1 <= a,b <= 100,000 // 제한시간 1초
def countPrime(minNum, maxNum) :
    count =0 
    for i in range(minNum,maxNum+1) :
        if isPrime(i) == True :
            count += 1
    return count
    
######### 최댓값 반환 ##########
'''
test = maxMachine()
test.addNumber(3)
test.addNumber(15)
test.addNumber(7)
test.addNumber(12)

print(test.getMax())
'''
######### 행렬 문제 ##########
'''
test2 = myMatrix()
test2.inputNum()
test2.printResult()
'''
######### 소수 판단 ##########
'''
import math
print(isPrime(int(input())))
'''
######### 소수 카운팅 ##########
print(countPrime(5,34))   ## 에라토네스의 체를 이용하면 빠르다고 함.