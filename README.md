# processing-element
A configurable processing element for arbitrary deep neural network accelerators. Implemented in Scala w/ Chisel [1].

*Version 2 Proposal.*

## Overview
Arrays of processing elements (PE) are at the heart of many deep neural network (DNN) accelerator designs [2]. This implementation provides a PE generator function: one that, once configured, will generate a Chisel Module for FIRRTL or Verilog for a PE. The goal of this design is to both unify existing PEs and to generalize to new ones. It does this by leveraging a configurable network-on-chip (NoC) between submodules of the PE. 

## Design
In addition to a control unit and NoC, the PE has five modules connected via a configurable NoC:
1. an interface with the external array,
2. a register file (RF),
3. a SIMD adder,
4. a SIMD multiplier,
5. and a nonlinear functional unit.

### NoC
#### Router
Every module is configured with a router in and out. As the connection between any two modules may or may not be there, every router is of a configurable in:out ratio. Each router must be configured
#### Links
Each of the links is equipped with a configurable register chain to facilitate pipelining. Every link may also vary in terms of width and bitwidth; *i.e.* it will be width * bitwidth wires wide. Link width and bitwidth is automatically set to match the module interfaces. 

### Register File
The register file is of configurable
1. memory size,
2. bitwidth,
3. write ports,
4. and read ports.
All read/write ports act simultaneously (how do we do half cycle r/w?), so it is up to addressing to avoid hazards.

### Adder
The adder is slightly complicated; this is to enable both SIMD MACs and SIMD additions. It is of a configurable input width and bitwidth. It can also be configured to add in parallel, as a tree, or both. For example, one configured to do both may have 256 input pairs, but based on a control signal, will either reduce groups of 16 to 16 outputs or simply add all 256 pairs in parallel. One configured as a 256:1 tree may simply always reduce 256 inputs to one output.

## References
[1] J. Bachrach, H. Vo, B. Richards, Y. Lee, A. Waterman, R. Avizienis, J. Wawrzynek, K. Asanovic, "Chisel: Constructing Hardware in a Scala Embedded Language," in *Design Automation Conference*, 2012
[2] V. Sze, T.-J. Yang, Y.-H. Chen, and J. Emer, "Efficient Processing of Deep Neural Networks: A Tutorial and Survey," in *Proc. IEEE*, vol. 105, no. 12, pp. 2295-2329, Dec 2017  
