# Guava Preconditions

Guava Preconditions class provides a list of static method for checking the value incoming method or constructor

these static method can be divided into three categories: 

+ no arguments
+ an extra Object argument
+ an extra String argument: usage like `printf` but only allow `%s` indicators (占位符)

## checkArgument

this method accepts a boolean condition

usage can look:
 
test class:`cn.luckycurve.basic.preconditions.CheckArgument`


## checkElementIndex

implements very simple, input index and size, this method will compare, if pass, it will return pass result, else throw IndexOutOfBoundsException


## checkNotNull

if null, throw NullPointerException, see `cn.luckycurve.basic.preconditions.CheckNotNullTest`


## checkPositionIndex

same as method checkElementIndex, it just includes size we pass


## checkState

same as method checkArgument,it just different in mean, it throws IllegalStateException