# Overview
Gold Touch is a project that uses thin, durable and cheap gold leaf tattoos to digitally sense user input and communicates with a Bluetooth device. 

# Setup
A gold leaf tattoo is attached via a thin wire to a circuit controlled by an Arduino. The Arduino detects user touch on the gold leaf and transmits data via Bluetooth to a connected device. 

# Circuit
An Arduino send pin is connected to an Arduino receive pin. This connection has a relatively high resistance (We used just over 1 Megaohm of resistors). Branching off from this connection between the two pins is a connection to the gold leaf tattoo. 

# Detection
The Arduino detects user touch by measuring the time it takes for a current change from the send pin, to affect the receive pin. The gold leaf acts as a capacitor, slowing this down. (i.e. when the send pin reduces its outputted charge, charge flows from the gold leaf out into the primary connection, giving the receive pin a few fractions of a second longer of high charge. Conversely, when the send pin increases its outputted charge, charge flows from the primary connection into the gold leaf, delaying when the receive pin feels this increased charge). This delay should be relatively constant. However, when the user touches the gold leaf, their body can act as a further charge sink, increasing the capacitance and thus the delay. This is because more charge can flow into or out of the gold leaf/hand combination than the gold leaf alone. The Arduino can thus interpret an increased delay as a user touch. 

# Software
We coded the Arduino to interpret delay changes and either a touch event or simple interference (this was much more difficult than anticipated). Additionally, we designed a simple protocol for our Arduino to communicate over Bluetooth and created an Android library to implement this.

# Durability
In our testing none of our tattoos have come off or started to fade throughout the hackathon. We predict that they will remain on through normal use and perhaps even through bathing. 

# Testing
We created a silly Smash themed Flappy Bird clone to showcase our work and demonstrate its effectiveness. 

# Practical Uses
While our Arduino/battery/breadboard contraption is rather bulky, significant resources would allow this setup to be reduced to a tiny integrated circuit connected to a watch battery, and connect via small nearly-invisible wires, rather than our current ketchup and mustard themed ones. This would allow the setup to be worn, unnoticeable by either the viewer or the wearer. Furthermore, an electric cutter would allow the creation of more precise art than our X-ACTO knife. Finally, multiple tattoo points could be used, allowing more complicated control. This could lead to stylistic, convenient tattoo communication systems. From here the opportunities are endless. Perhaps a user can be able to pause music when their phone is buried in their pocket or across the room. Perhaps users could have a quick, "Ringer Off" button to avoid having their phone disrupt a sudden important task. Perhaps the power users might set up their own insane set of responses in Tasker or similar apps. Importantly this technology is widely accessible-- the cost of <$5 initially and <1 cent per tattoo allows anyone who has applied a temporary tattoo and can plug in a wire to use Gold Touch. 

# Thanks
Many thanks to Cindy Kao, Christian Holz, Asta Roseway, Andres Calvo, and Chris Schmandt from the MIT Media Lab and Microsoft research for inspiration, and the authors of countless stackoverflow posts for saving our sanity.
