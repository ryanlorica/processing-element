# processing-element
A course-grained reconfigurable architecture-based processing element for deep neural network accelerators. To be implemented in Scala w/ Chisel3 [1].

*Version 2*

## Overview
Arrays of processing elements (PEs) are at the heart of many deep neural network (DNN) accelerator designs [2]. This implementation presents a flexible PE design intended to emulate existing dataflows as well as generalize to emerging ones. It allows configuration both post- and pre- synthesis. The pre-synthesis configurability consists of bit-widths, SIMD parameters, number representation, multiplier/adder type, and module existence. Post-synthesis configuration consists of data routing and function selection. In other words, topological configurations are set pre-synthesis, dataflow configurations are set post-synthesis.

## Microarchitecture and Configurability

<img align="right" src="img/top-level-arch.png" width="500">

The PE microarchitecture consists of six* potential modules and a "switcher". These six modules are
1. a register file for weights,
2. a register file for activations,
3. a register file for partial sums,
4. a SIMD adder,
5. a SIMD multiplier,
6. and a nonlinear function unit.

The switcher connects these and determines connectvity.

\* A SIMD FMA is planned

### Global Configurations

#### SIMD Parameters
The SIMD functionality of the PE is encoded in two parameters: the dimension (m) and number (n) of the vector inputs. This is best explained via an example. Suppose the desired operation is a 3x3 convolution on a 4x4 feature map--to be done completely simultaneously. Since the kernel is a 9 dimensional vector, this sets m to 9. Since the convolution is composed of 4 inner products, this sets n to 4. So, to complete this convolution all at once, the parameters should be 9 and 4. Using these same parameters on a 9x9 kernel and 10x10 feature map would result in computing the 4 outputs in parallel in 9 steps (81 dimension inner product done 9 components at a time). The same parameters on a 3x3 kernel and a 6x6 feature map results in the computation requiring 4 steps (16 inner products done 4 at a time). Finally, the same parameters on a 9x9 kernel and 12x12 feature map results in a 36 step convolution (16 inner products done 4 at a time, with each inner product taking 9 steps).

#### Bit-Width and Number Representation
Pre-synthesis, the PE supports a set of *fixed-width* datapaths for both integers and float, namely 8-bit, 16-bit, 32-bit and 64-bit datapaths. Note that the width of the datapath is both the input and output width, unlike architectures such as Google's TPU, whose systolic array has a 16-bit input and 32-bit output [3].

### Modules and Module-Specific Configurations

#### Switcher
The modules are connected via an all-to-all topology, controlled via the central switcher. Data and timing is handled via a producer/consumer pattern. Producers cast their outputs to the switcher until taken by a consumer, automatically facilitating pipelining. The switcher sets multiplexers based on the producer/consumer relationships of each module, enabling single-, multi-, and broadcasting data. The producer/consumer relationships are held in a small register file in the switcher; each module has a dedicated register that holds the address of the sources it draws from. Since there are likely many cycles in between processing different DNN layers, this CGRA approach minimizes the overhead routing power cost.

The addresses of each module are shown below.

| Module           | Address |
| ---------------- | ------- |
| I/O              | 0       |
| Weight RF        | 1       |
| Activation RF    | 2       |
| Multiplier Block | 3       |
| Adder Block      | 4       |
| Partial Sum RF   | 5       |
| Nonlinear Unit   | 6       |

#### Register Files
There are potentially register files for weights, activations, and partial sums. The only module-specific specific configuration is register size. The number of ports is automatically set via the global SIMD configuration. For the weight and activation RFs, there will be *m * n* input/output ports. For the partial sum RF there will only be *n* ports. For post-synthesis configurability, *each* of these ports must be controlled via read/write enable and address signals.

#### Adder Block
To enable both SIMD MACs and SIMD additions, the adder is slightly complicated. Pre-synthesis, its capabilities are set: adding in parallel, as a tree, or both. One configured to add in parallel will have two *m * n* input lines and output the *m * n* sums. One configured to add as a tree will reduce each *m* dimensional vector to a single sum, *i.e.* it maps *m * n* inputs to *n* outputs. One configured to do both will have a control signal and local register that dictates which it performs.

#### Multiplier Block
The multiplier block is simply a collection of *m * n* multipliers that operate in parallel. The amount and type of multiplier is set by the global configurations.

#### Nonlinear Function Unit
Pre-synthesis configuration for the nonlinear unit consists of determining function sets and implementations. As of right now, it only supports ReLu for n-bit integers.

## References
[1] J. Bachrach, H. Vo, B. Richards, Y. Lee, A. Waterman, R. Avizienis, J. Wawrzynek, K. Asanovic, "Chisel: Constructing Hardware in a Scala Embedded Language," in *Design Automation Conference*, 2012

[2] V. Sze, T.-J. Yang, Y.-H. Chen, and J. Emer, "Efficient Processing of Deep Neural Networks: A Tutorial and Survey," in *Proc. IEEE*, vol. 105, no. 12, pp. 2295-2329, Dec 2017

[3] N. P. Jouppi and et al, "In-datacenter performance analysis of a tensor processing unit," in *International Symposium of Computer Architecture*, 2017

[4] Y.-H. Chen, T. Krishna, J. Emer, V. Sze, "Eyeriss: An Energy-Efficient Reconfiurable Accelerator for Deep Convolutional Neural Netowrks," *IEEE Journal of Solid-State Circuits*, vol. 52, pp. 127-138, 2016
