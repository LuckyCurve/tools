# Throwables

this class conatins a set of static method to deal with two situations:
+ propagation
+ process the cause chain

the reason why we need to propagate Throwable because if we call a method and throw a Throwable,in most cause we want to convert it to RuntimeException but for Some special Exception we just want to direct throw


## propagateIfPossible

we want to box some exception to RuntimeException that when we call this method, we do not need to process exception


## Causal Chain

main method: getRootCause, getCausalChain[outer...inner], getStackTraceAsString, usage can ses `cn.luckycurve.basic.throwables.PropagateTest`