# processing-element
A configurable processing element for arbitrary deep neural network accelerators.

*Version 2.*

## Overview
Arrays of processing elements (PE) are at the heart of many deep neural network (DNN) accelerator designs [1]. This implementation provides a PE generator function: one that, once configured, will generate a Chisel FIRRTL or Verilog for a pipelined PE. The goal of this design is, when coupled with a simulator, to enable a *programmatically searchable design space*; *i.e.,* act as the first step toward implementing a DNN accelerator generator. 

## Interface
TODO. Everything is TODO.

## References
[1] V. Sze, T.-J. Yang, Y.-H. Chen, and J. Emer, "Efficient Processing of Deep Neural Networks: A Tutorial and Survey," in *Proc. IEEE*, vol. 105, no. 12, pp. 2295-2329, Dec 2017  
