# processing-element
A course-grained reconfigurable architecture-based processing element for deep neural network accelerators. Implemented in Scala w/ Chisel3 [1].

*Version 2 Proposal.*

## Overview
Arrays of processing elements (PE) are at the heart of many deep neural network (DNN) accelerator designs [2]. This implementation presents a novel PE design intended to emulate existing dataflows as well as generalize to new ones. It allows configuration both post- and pre- synthesis. The pre-synthesis configurability consists of bit-widths, multiplier/adder type, and module existence. Post-synthesis configuration consists of data routing and function selection.

## Microarchitecture
The PE microarchitecture consists of seven modules and a minature NoC. The simple NoC routes data according to routing registers that can be configured post-synthesis. Since there are likely many cycles in between processing different DNN layers, this CGRA approach minimizes routing power cost. The PE's seven modules are
1. an interface with the external array,
2. a register file for weights,
3. a register file for activations,
4. a register file for partial sums,
5. a SIMD adder,
6. a SIMD multiplier,
7. and a nonlinear functional unit.

### Network-on-Chip
#### Router
Every module has a specialized router for I/O. The router each router has a configuration register that contains information for routing. For example, for to emulate the Eyeriss PE [3], the weight RF and activation RF output to the multiplier, the multiplier outputs to the adder, the adder outputs to the partial sum register and IO module, and the partial sum register outputs to the adder. This  
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
[3] Y.-H. Chen, T. Krishna, J. Emer, V. Sze, "Eyeriss: An Energy-Efficient Reconfiurable Accelerator for Deep Convolutional Neural Netowrks," *IEEE Journal of Solid-State Circuits*, vol. 52, pp. 127-138, 2016
