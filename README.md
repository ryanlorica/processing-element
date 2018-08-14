# processing-element
A course-grained reconfigurable architecture-based processing element for deep neural network accelerators. Implemented in Scala w/ Chisel3 [1].

*Version 2 Proposal.*

## Overview
Arrays of processing elements (PE) are at the heart of many deep neural network (DNN) accelerator designs [2]. This implementation presents a novel PE design intended to emulate existing dataflows as well as generalize to new ones. It allows configuration both post- and pre- synthesis. The pre-synthesis configurability consists of bit-widths, number representation, multiplier/adder type, and module existence. Post-synthesis configuration consists of data routing and function selection. In other words, topological configurations are set pre-synthesis, dataflow configurations are set post-synthesis.

## Microarchitecture
The PE microarchitecture consists of seven modules and a minature NoC. The post-synthesis configurability is leverages an all-to-all NoC between the seven modules. The NoC is configured via reprogrammable registers to route data along specific paths. Since there are likely many cycles in between processing different DNN layers, this CGRA approach minimizes the overhead routing power cost. The PE's seven modules are
1. an interface with the external array,
2. a register file for weights,
3. a register file for activations,
4. a register file for partial sums,
5. a SIMD adder,
6. a SIMD multiplier,
7. and a nonlinear functional unit.

### Network-on-Chip
#### Router
Every module has a specialized router for I/O. The router each router has a configuration register that contains information for routing. For example, for to emulate the Eyeriss PE [3], the weight RF and activation RF output to the multiplier, the multiplier outputs to the adder, the adder outputs to the partial sum register and IO module, and the partial sum register outputs to the adder. Pipelining is automatically facilitated through Decoupled (ready/valid) interfaces between each router and the entrance to the PE. 
#### Links
The links between the routers are of configurable bit-widths and data representations, *e.g.* 8-bit signed int, 16-bit floating point, 64-bit floating point, *etc.* Each link is not configured directly, but is configured via a global "data path" configuration.

### Register Files
The register file is of configurable
1. memory size,
2. bit-width,
3. data representation,
4. number of inputs,
5. and number of outputs.

The number of inputs and outputs is automatically set via a global SIMD configuration.

### Adder Block
The adder is slightly complicated; this is to enable both SIMD MACs and SIMD additions. It is of a configurable input width and bitwidth. It can also be configured pre-synthesis to be able to add in parallel, as a tree, or both. For example, one configured to do both may have 256 input pairs, but based on a control signal, will either reduce groups of 16 to 16 outputs or simply add all 256 pairs in parallel. One configured as a 256:1 tree may simply always reduce 256 inputs to one output.

### Multiplier Block
The multiplier block is simply a collection of multipliers that operate in parallel. The amount of multipliers is set pre-synthesis. The multiplier implementation is also configurable. *E.g.* it can be a 2-cycle 16-bit floating point multiplier or a 1-cycle 8-bit integer multiplier. 

### Nonlinear Functional Unit
Pre-synthesis configuration for the nonlinear unit consists of determining function sets and implementations. As of right now, it only supports ReLu for n-bit integers.

## References
[1] J. Bachrach, H. Vo, B. Richards, Y. Lee, A. Waterman, R. Avizienis, J. Wawrzynek, K. Asanovic, "Chisel: Constructing Hardware in a Scala Embedded Language," in *Design Automation Conference*, 2012
[2] V. Sze, T.-J. Yang, Y.-H. Chen, and J. Emer, "Efficient Processing of Deep Neural Networks: A Tutorial and Survey," in *Proc. IEEE*, vol. 105, no. 12, pp. 2295-2329, Dec 2017
[3] Y.-H. Chen, T. Krishna, J. Emer, V. Sze, "Eyeriss: An Energy-Efficient Reconfiurable Accelerator for Deep Convolutional Neural Netowrks," *IEEE Journal of Solid-State Circuits*, vol. 52, pp. 127-138, 2016
