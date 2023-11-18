# Kpi.KBlinkt

> A demo how to work with Kotlin Native to access GPIO pins on a Raspberry Pi.

## tl;dr

KPi.Blinkt is a sample how to use Kotlin Native on a Raspberry Pi to access and control attached HATs using the GPIO
connector. It's build on top of the [ktgpio](https://github.com/ktgpio/ktgpio/) library.

In many parts, this is a port of
my [HomeBear.Blinkt](https://github.com/tscholze/dotnet-iot-homebear-blinkt/tree/master/HomeBear.Blinkt/Controller)
Windows 10 IoT C# app I wrote times ago.

## Required Hardware

- 64bit Raspberry Pi (Model 3B and later)
- [Pimoroni Blinkt!](https://shop.pimoroni.com/products/blinkt) Raspberry Pi HAT

## Required Software

- Windows, Mac or Linux host
- Installed SSH client, mostly build into the host OS
- Raspbian 64bit or other Raspberry Pi OS with GPIO libaries
- JetBrains Fleet, IntelliJ (Community) or similar to edit Kotlin code

## How to run

Open the `build_deploy.start.sh` file, edit `PI_HOST` property and click the play symbol on the first line of the file.

## Available `curl` commands
After the server has been started, the following commands are available:
- `http://pi:8080/on` - Turns all LEDs on (sets white color)
- `http://pi:8080/off` - Turns all LEDs off (sets black color)

All curl commands can be found in `curl_requests_run.sh` file.

## Features
- [x] Project setup
- [x] Deployment to a Pi using a convenient shell script
- [x] Control LEDs using Kotlin functions
- [x] Add "plugin"-system for light modes
- [x] Control LED using remote computer's `curl` commands
- [ ] Make it fancy

## Contributing

Feel free to improve the quality of the code. It would be great to learn more from experienced Kotlin and IoT
developers.

## Authors

Just me, [Tobi]([https://tscholze.github.io).

## Special thanks to

BitSpittle, CharLEE, Clovis and TheDome from the [Kobweb Discord](https://discord.com/invite/5NZ2GKV5Cs) to support Kotlin beginners
like me.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE.md) file for details.
Dependencies or assets maybe licensed differently.
