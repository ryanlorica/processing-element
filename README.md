# processing-element
A processing element for course-grained reconfigurability in deep neural network accelerators. Implemented in Scala w/ Chisel [1].

*Version 2 Proposal.*

## Overview
Arrays of processing elements (PE) are at the heart of many deep neural network (DNN) accelerator designs [2]. This implementation provides a PE generator function: one that, once configured, will generate a Chisel Module for FIRRTL or Verilog for a PE. The goal of this design is to both emulate existing PEs and to generalize to new ones. Ideally, it will enable programmatic design-space exploration.


<img src="img/top-level-arch.png" width="720">

## Design Philosophy
With Version 1, the primary design trade-offs were between expressive power of configuration and simplicity of use; the balance was tilted severely toward expressive power. However, for Version 2, precise topological configuration is sacrificed for both representational power and simplicity of use. With very little mandatory configuration, the PE-as-is supports a wide variety of data flows, albeit with routing overhead. Programming-like configuration is gone; one only has to set connections, SIMD widths, and function sets. Pipelining is automatically optimized and internal control is entirely predefined; the PE is controlled via a limited set of external control signals.

## Microarchitecture
The PE microarchitecture consists of five modules and a relatively simple NoC. To enable arbitrary data flows, the NoC passes packets consisting of both data and routing instructions. 
In addition to a control unit and NoC, the PE has five modules connected via a configurable NoC:
1. an interface with the external array,
2. a register file (RF),
3. a SIMD adder,
4. a SIMD multiplier,
5. and a nonlinear functional unit.

### NoC
#### Router
Every module has a router for I/O.  
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

## Control
The PE has a decoder that will decode a non-configurable set of input signals. The set of possible input signals is limited to
1. Load Partial Sum,
2. Load Weight,
3. Load Activation,
4. Convolve,
5. Classify,
2. Normalize,
3. and Pool.
Control signals are pipelined identically to the data, allowing pipelined control.

## References
[1] J. Bachrach, H. Vo, B. Richards, Y. Lee, A. Waterman, R. Avizienis, J. Wawrzynek, K. Asanovic, "Chisel: Constructing Hardware in a Scala Embedded Language," in *Design Automation Conference*, 2012
[2] V. Sze, T.-J. Yang, Y.-H. Chen, and J. Emer, "Efficient Processing of Deep Neural Networks: A Tutorial and Survey," in *Proc. IEEE*, vol. 105, no. 12, pp. 2295-2329, Dec 2017  
