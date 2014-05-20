#import <Cordova/CDV.h>
#import "CardIO.h"


@interface CardIOPGPlugin : CDVPlugin<CardIOPaymentViewControllerDelegate>

- (void)scan:(CDVInvokedUrlCommand *)command;
- (void)canScan:(CDVInvokedUrlCommand *)command;
- (void)version:(CDVInvokedUrlCommand *)command;

@end
