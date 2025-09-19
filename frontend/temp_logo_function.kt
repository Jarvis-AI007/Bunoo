@Composable
private fun PaymentMethodLogo(method: PaymentMethod) {
    when (method) {
        PaymentMethod.CARD -> {
            // Credit/Debit Card logo with overlapping cards
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .border(1.dp, Color(0xFFE5E5E5), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(-4.dp)) {
                    // Back card (yellow)
                    Box(
                        modifier = Modifier
                            .size(20.dp, 12.dp)
                            .background(Color(0xFFFFD700), RoundedCornerShape(2.dp))
                    )
                    // Front card (blue with chip)
                    Box(
                        modifier = Modifier
                            .size(20.dp, 12.dp)
                            .background(Color(0xFF1976D2), RoundedCornerShape(2.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp, 4.dp)
                                .background(Color(0xFFFFD700), RoundedCornerShape(1.dp))
                                .align(Alignment.TopStart)
                                .padding(1.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(Color(0xFFD32F2F))
                                .align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
        PaymentMethod.UPI -> {
            // UPI logo with "PI" text
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(Color(0xFFFF6B35), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "PI",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        PaymentMethod.NET_BANKING -> {
            // Bank building logo
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(Color(0xFF757575), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Bank building
                    Box(
                        modifier = Modifier
                            .size(16.dp, 8.dp)
                            .background(Color.White, RoundedCornerShape(1.dp))
                    ) {
                        // Columns
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            repeat(3) {
                                Box(
                                    modifier = Modifier
                                        .width(1.dp)
                                        .fillMaxHeight()
                                        .background(Color(0xFF757575))
                                )
                            }
                        }
                    }
                    // Roof
                    Box(
                        modifier = Modifier
                            .size(18.dp, 2.dp)
                            .background(Color.White, RoundedCornerShape(1.dp))
                    )
                }
            }
        }
        PaymentMethod.CASH -> {
            // Cash/Rupee logo
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(Color(0xFFFFA000), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "₹",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
